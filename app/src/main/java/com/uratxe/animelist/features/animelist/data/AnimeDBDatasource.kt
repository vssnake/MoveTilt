package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Response
import com.uratxe.AnimeListQuery
import com.uratxe.mvit.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.Flow

class AnimeDBDatasource : AnimeDataSource {
    override fun getAnimes(page: Int): Flow<Either<Failure, AnimeListQuery.Data>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}