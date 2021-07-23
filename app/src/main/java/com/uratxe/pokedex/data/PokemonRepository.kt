package com.uratxe.pokedex.data

import com.unatxe.commons.data.ApiError
import com.unatxe.commons.utils.Either
import com.unatxe.commons.utils.map
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.*

class PokemonRepository(private val api: PokemonDataSource, private val db: PokemonPersistenceDataSource) {

    fun allPokemons(): Flow<Either<ApiError, List<Pokemon>>>  {
        return if(!db.arePokemonsLoaded()) {
            api.allPokemons().map {
                if (it.isRight) {
                    it.either({},{ pokemonList ->
                        db.addPokemons(pokemonList)
                    })
                }
                it
            }
        } else db.allPokemons()
    }

    fun pokemonDetail(id: Int): Flow<Either<ApiError, Pokemon>> {
        return if(!db.hasPokemonDetail(id)) {
            api.pokemonDetail(id).map {
                if (it.isRight) {
                    it.either({},{ pokemon ->
                        db.addPokemonDetail(pokemon)
                    })
                }
                it
            }
        } else db.pokemonDetail(id)
    }
}