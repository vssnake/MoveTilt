package com.uratxe.animelist.features.animelist.data

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toFlow
import com.uratxe.AnimeListQuery
import com.uratxe.animelist.data.Apollo
import com.uratxe.mvit.Either
import com.uratxe.mvit.exception.Failure
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.*


class AnimeRepository(private val animeApiDataSource : AnimeDataSource,private val animeDbDataSource: AnimeDataSource) {



    fun getAnimes(page : Int): Flow<Either<Failure, AnimeListQuery.Data>> {

        return animeApiDataSource.getAnimes(page)
    }





}




