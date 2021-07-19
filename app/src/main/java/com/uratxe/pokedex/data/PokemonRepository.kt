package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.unatxe.commons.utils.runIO
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class PokemonRepository(private val api: PokemonDataSource, private val db: PokemonDataSource) {

    fun allPokemons(): Flow<Either<ApiError, List<Pokemon>>>  {
        return api.allPokemons().map { result ->
            result.mapRight { Pokemon.mapList(it) }
        }
    }

    fun pokemonDetail(): Flow<Either<ApiError, Pokemon>> {
        return emptyFlow() /*api.pokemonDetail().map { result ->
            result
        }*/
    }
}