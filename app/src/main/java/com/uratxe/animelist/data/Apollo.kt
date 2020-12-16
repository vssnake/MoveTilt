package com.uratxe.animelist.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.unatxe.mvvmi.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.FlowCollector
import okhttp3.OkHttpClient


object Apollo {


    private val clienUrl = "https://graphql.anilist.co"

    private val okHttpClient by lazy{ OkHttpClient() }

    val apolloClient by lazy {ApolloClient.builder()
        .serverUrl(clienUrl)
        .okHttpClient(okHttpClient)
        .build()}


    fun <TypeResponse> apolloCallChecker(): suspend (Response<TypeResponse>) -> com.unatxe.mvvmi.Either<Failure, TypeResponse> {
        return {
            val data =  if (it.hasErrors()){
                com.unatxe.mvvmi.Either.Left(
                    ApolloFailure(
                        it.errors()
                    )
                )
            }else{
                com.unatxe.mvvmi.Either.Right(it.data()!!)
            }
            data
        }
    }

    val  catchApolloError: suspend FlowCollector<com.unatxe.mvvmi.Either.Left<Failure>>.(cause: Throwable) -> Unit =  { exception ->
        emit(com.unatxe.mvvmi.Either.Left(Failure.ServerError(exception)))
    }


}

class ApolloFailure(val errors : List<Error>) : Failure.FeatureFailure()