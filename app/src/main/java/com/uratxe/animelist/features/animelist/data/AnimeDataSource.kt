package com.uratxe.animelist.features.animelist.data

import com.uratxe.AnimeListQuery
import com.uratxe.core.data.exceptions.Failure
import com.uratxe.core.utils.Either
import kotlinx.coroutines.flow.Flow

interface AnimeDataSource {

    fun getAnimes(page : Int) : Flow<Either<Failure, AnimeListQuery.Data>>

}