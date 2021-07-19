package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun allPokemons(): Flow<Either<ApiError, List<FeatureLinkDTO>>>
    fun pokemonDetail(id: Int): Flow<Either<ApiError, Pokemon>>
}