package com.uratxe.animelist.features.list.data

import com.uratxe.AnimeListQuery
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import kotlinx.coroutines.flow.Flow

interface AnimeDataSource {
    fun getAnimes(page : Int) : Flow<Either<Failure, AnimeListQuery.Data>>
}