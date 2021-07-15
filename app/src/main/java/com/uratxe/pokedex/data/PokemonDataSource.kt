package com.uratxe.pokedex.data

import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonDataSource {
    fun allPokemons(): Flow<List<Pokemon>>
}