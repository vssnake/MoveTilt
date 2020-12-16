package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.data.Apollo
import com.uratxe.animelist.data.Apollo.apolloCallChecker
import com.uratxe.animelist.data.Apollo.catchApolloError
import com.unatxe.mvvmi.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AnimeApiDataSource : AnimeDataSource {
    override fun getAnimes(page: Int): Flow<com.unatxe.mvvmi.Either<Failure, AnimeListQuery.Data>> {
        return Apollo.apolloClient.query(AnimeListQuery(id = Input.absent(),
            page = Input.optional(page),
            perPage = Input.optional(50),
            search = Input.absent()))
            .toFlow()
            .map(apolloCallChecker<AnimeListQuery.Data>())
            .catch(catchApolloError)
    }
}