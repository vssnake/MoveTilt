package com.uratxe.animelist.features.animelist.data

import com.uratxe.AnimeListQuery
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import kotlinx.coroutines.flow.Flow

class AnimeDBDatasource : AnimeDataSource {
    override fun getAnimes(page: Int): Flow<Either<Failure, AnimeListQuery.Data>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}