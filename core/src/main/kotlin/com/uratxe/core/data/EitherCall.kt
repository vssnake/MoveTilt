package com.uratxe.core.data

import com.uratxe.core.utils.Either
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.lang.reflect.Type


class EitherCall<R>(
    private val delegate: Call<R>,
    private val successType: Type
) : Call<Either<ApiError, R>> {

    override fun enqueue(callback: Callback<Either<ApiError, R>>) = delegate.enqueue(
        object : Callback<R> {

            override fun onResponse(call: Call<R>, response: Response<R>) {
                callback.onResponse(this@EitherCall, Response.success(response.toEither()))
            }

            private fun Response<R>.toEither(): Either<ApiError, R> {
                // Http error response (4xx - 5xx)
                if (!isSuccessful) {

                    val errorBody = errorBody()?.string() ?: ""

                    return Either.Left(HttpError(code(), errorBody, this))
                }

                // Http success response with body
                body()?.let { body -> return Either.Right(body) }

                // if we defined Unit as success type it means we expected no response body
                // e.g. in case of 204 No Content
                return if (successType == Unit::class.java) {
                    @Suppress("UNCHECKED_CAST")
                    Either.Right(Unit) as Either<ApiError, R>
                } else {
                    @Suppress("UNCHECKED_CAST")
                    Either.Left(UnknownError("Response body was null")) as Either<ApiError, R>
                }
            }

            override fun onFailure(call: Call<R>, throwable: Throwable) {

                val error = when (throwable) {
                    is IOException -> NetworkError(throwable, call)
                    else -> UnknownApiError(throwable, call)
                }
                callback.onResponse(this@EitherCall, Response.success(Either.Left(error)))
            }
        }
    )

    override fun clone(): Call<Either<ApiError, R>> {
        return EitherCall(delegate.clone(), successType)
    }

    override fun execute(): Response<Either<ApiError, R>> {
       throw UnsupportedOperationException()
    }

    override fun isExecuted(): Boolean {
        return delegate.isExecuted
    }

    override fun cancel() {
       delegate.cancel()
    }

    override fun isCanceled(): Boolean {
        return delegate.isCanceled
    }

    override fun request(): Request {
        return delegate.request()
    }

    override fun timeout(): Timeout {
        return delegate.timeout()
    }


// override remaining methods - trivial
}


class EitherCallAdapter<R>(
    private val successType: Type
) : CallAdapter<R, Call<Either<ApiError, R>>> {

    override fun adapt(call: Call<R>): Call<Either<ApiError, R>> = EitherCall(call, successType)

    override fun responseType(): Type = successType
}