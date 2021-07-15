package com.uratxe.pokedex.data

import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.data.dto.PokemonDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/?limit=2000")
    fun getPokemons(): Call<List<FeatureLinkDTO>>

    @GET("pokemon/{id}")
    fun getPokemon(@Path("id") id: Int): Call<PokemonDTO>
}

