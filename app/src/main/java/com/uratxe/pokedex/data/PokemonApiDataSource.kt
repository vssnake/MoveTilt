package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.unatxe.commons.utils.map
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.data.services.PokemonService
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PokemonApiDataSource(private val service: PokemonService): PokemonDataSource {
    override fun allPokemons(): Flow<Either<ApiError, List<Pokemon>>> {
        return flow {
            val response = service.getPokemons()
            emit(response.map { Pokemon.mapList(it.results) })
        }.flowOn(Dispatchers.IO)
    }

    override fun pokemonDetail(id: Int): Flow<Either<ApiError, Pokemon>> {
        return flow {
            val response = service.getPokemon(id)
            emit(response.map { Pokemon(id, "Prueba") })
        }
    }
}