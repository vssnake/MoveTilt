package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.Either
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PokemonDBDataSource: PokemonDataSource {
    override fun allPokemons(): Flow<Either<ApiError, List<FeatureLinkDTO>>> {
        TODO("Not yet implemented")
    }

    override fun pokemonDetail(id: Int): Flow<Either<ApiError, Pokemon>> {
        TODO("Not yet implemented")
    }
}