package com.uratxe.core.data

import retrofit2.Call
import retrofit2.Response

public sealed class ApiError

data class HttpError(val code: Int, val body: String, val response: Response<*>) : ApiError()

data class NetworkError(val throwable: Throwable, val call: Call<*>) : ApiError()

data class UnknownApiError(val throwable: Throwable, val call: Call<*>) : ApiError()