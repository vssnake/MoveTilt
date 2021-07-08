package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.toFlow
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.data.Apollo
import com.uratxe.animelist.data.Apollo.catchApolloError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.uratxe.animelist.data.Apollo.apolloCallChecker
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
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