package com.uratxe.pokedex.data

import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

class PokemonApiDataSource(private val service: PokemonService): PokemonDataSource {
    override fun allPokemons(): Flow<List<Pokemon>> {
        TODO("Not yet implemented")
    }

}