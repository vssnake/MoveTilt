package com.uratxe.pokedex.data

import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PokemonRepository(private val api: PokemonApiDataSource, private val db: PokemonDBDataSource) {

    fun allPokemons(): Flow<List<Pokemon>> {
        return emptyFlow()
    }
}