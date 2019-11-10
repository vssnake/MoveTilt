package com.uratxe.animelist.features.animelist

import android.util.Log
import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toFlow
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.data.Apollo
import com.uratxe.mvit.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*
import java.lang.Exception


class AnimeRepository {


    @InternalCoroutinesApi
    suspend fun animes()  {




        Apollo.apolloClient.query(AnimeListQuery(id = Input.absent(),
            page = Input.optional(1),
            perPage = Input.optional(-1),
            search = Input.absent()))
            .toFlow()
            .map(apolloCallChecker<AnimeListQuery.Data>())
            .catch(catchApolloError())
            .collect {

            }
    }

    fun <TypeResponse> apolloCallChecker(): suspend (Response<TypeResponse>) -> Either<Failure, Response<TypeResponse>> {
        return {
            val data =  if (it.hasErrors()){
                Either.Left(ApolloFailure(it.errors()))
            }else{
                Either.Right(it)
            }
            data
        }
    }

    fun catchApolloError(): suspend FlowCollector<Either.Left<Failure>>.(cause: Throwable) -> Unit {
        return { exception ->
            emit(Either.Left(Failure.ServerError(exception)))
        }
    }



    class ApolloFailure(val errors : List<Error>) : Failure.FeatureFailure()
}



