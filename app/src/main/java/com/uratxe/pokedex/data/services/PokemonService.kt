package com.uratxe.pokedex.data.services

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.uratxe.pokedex.data.dto.AllPokemonsResponseDTO
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.data.dto.PokemonDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/?limit=2000")
    suspend fun getPokemons(): Either<ApiError, AllPokemonsResponseDTO>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: Int): Either<ApiError,PokemonDTO>
}

