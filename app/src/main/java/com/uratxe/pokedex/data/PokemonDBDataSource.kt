package com.uratxe.pokedex.data

import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PokemonDBDataSource: PokemonDataSource {
    override fun allPokemons(): Flow<List<Pokemon>> {
        return emptyFlow()
    }
}