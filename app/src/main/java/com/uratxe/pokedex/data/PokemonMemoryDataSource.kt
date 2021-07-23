package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.utils.Either
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

class PokemonMemoryDataSource: PokemonPersistenceDataSource {
    private var pokemonsCached: MutableList<Pokemon> = mutableListOf()

    override fun allPokemons(): Flow<Either<ApiError, List<Pokemon>>> {
        return emptyFlow()
    }

    override fun arePokemonsLoaded(): Boolean {
        return pokemonsCached.isNotEmpty()
    }

    override fun hasPokemonDetail(id: Int): Boolean {
        return findPokemonByID(id)?.hasDetail ?: false
    }

    override fun addPokemons(pokemonList: List<Pokemon>) {
        pokemonsCached.addAll(pokemonList)
    }

    override fun addPokemonDetail(pokemon: Pokemon) {
        findPokemonByID(pokemon.id)?.apply {
            val position = pokemonsCached.indexOf(this)
            pokemonsCached[position] = pokemon
        }
    }

    override fun pokemonDetail(pokemonID: Int): Flow<Either<ApiError, Pokemon>> {
        return emptyFlow()
    }

    private fun findPokemonByID(id: Int): Pokemon? {
        return pokemonsCached.find { it.id == id }
    }
}