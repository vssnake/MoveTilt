package com.uratxe.animelist.features.animelist.data

import com.uratxe.AnimeListQuery
import com.unatxe.mvvmi.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.flow.Flow

interface AnimeDataSource {

    fun getAnimes(page : Int) : Flow<com.unatxe.mvvmi.Either<Failure, AnimeListQuery.Data>>

}