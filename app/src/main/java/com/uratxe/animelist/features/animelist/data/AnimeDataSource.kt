package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Response
import com.uratxe.AnimeListQuery
import com.uratxe.mvit.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.Flow

interface AnimeDataSource {

    fun getAnimes(page : Int) : Flow<Either<Failure, AnimeListQuery.Data>>

}