package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun allPokemons(): Flow<Either<ApiError, List<Pokemon>>>
    fun pokemonDetail(pokemonID: Int): Flow<Either<ApiError, Pokemon>>
}

interface PokemonPersistenceDataSource: PokemonDataSource {
    fun arePokemonsLoaded(): Boolean
    fun hasPokemonDetail(id: Int): Boolean
    fun addPokemons(pokemonList: List<Pokemon>)
    fun addPokemonDetail(pokemon: Pokemon)
}