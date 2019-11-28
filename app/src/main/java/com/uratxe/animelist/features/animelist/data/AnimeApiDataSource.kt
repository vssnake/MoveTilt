package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toFlow
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.data.Apollo
import com.uratxe.animelist.data.Apollo.apolloCallChecker
import com.uratxe.animelist.data.Apollo.catchApolloError
import com.uratxe.movetilt.mainPool
import com.uratxe.mvit.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map

class AnimeApiDataSource : AnimeDataSource {
    override fun getAnimes(page: Int): Flow<Either<Failure, AnimeListQuery.Data>> {
        return Apollo.apolloClient.query(AnimeListQuery(id = Input.absent(),
            page = Input.optional(page),
            perPage = Input.optional(50),
            search = Input.absent()))
            .toFlow()
            .map(apolloCallChecker<AnimeListQuery.Data>())
            .catch(catchApolloError)
    }
}