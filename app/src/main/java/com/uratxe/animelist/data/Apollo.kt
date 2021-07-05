package com.uratxe.animelist.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.uratxe.core.data.exceptions.Failure
import com.uratxe.core.utils.Either
import kotlinx.coroutines.flow.FlowCollector
import okhttp3.OkHttpClient


object Apollo {


    private val clienUrl = "https://graphql.anilist.co"

    private val okHttpClient by lazy{ OkHttpClient() }

    val apolloClient by lazy {ApolloClient.builder()
        .serverUrl(clienUrl)
        .okHttpClient(okHttpClient)
        .build()}


    fun <TypeResponse> apolloCallChecker(): suspend (Response<TypeResponse>) -> Either<Failure, TypeResponse> {
        return {
            val data =  if (it.hasErrors()){
                Either.Left(
                    ApolloFailure(
                        it.errors()
                    )
                )
            }else{
                Either.Right(it.data()!!)
            }
            data
        }
    }

    val  catchApolloError: suspend FlowCollector<Either.Left<Failure>>.(cause: Throwable) -> Unit =  { exception ->
        emit(Either.Left(Failure.ServerError(exception)))
    }


}

class ApolloFailure(val errors : List<Error>) : Failure.FeatureFailure()