package com.uratxe.animelist.features.animelist.data

import com.uratxe.AnimeListQuery
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import kotlinx.coroutines.flow.Flow


class AnimeRepository(private val animeApiDataSource : AnimeDataSource,private val animeDbDataSource: AnimeDataSource) {



    fun getAnimes(page : Int): Flow<Either<Failure, AnimeListQuery.Data>> {

        return animeApiDataSource.getAnimes(page)
    }





}




