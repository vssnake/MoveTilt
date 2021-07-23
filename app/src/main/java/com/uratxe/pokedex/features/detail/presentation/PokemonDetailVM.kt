package com.uratxe.pokedex.features.detail.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.unatxe.mvvmi.MVVMIData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface
import com.unatxe.mvvmi.ShowLoading
import com.uratxe.pokedex.data.PokemonRepository
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.collect

class PokemonDetailVM(aplication: Application, private val pokemonRepository: PokemonRepository)
    : MVVMIViewModel<PokemonDetailData>(aplication) {

    override fun onViewInitialized() {}

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        when(commands) {
            is LoadPokemonDetail -> loadPokemonDetail(commands.id)
        }
    }

    override fun initViewData(): PokemonDetailData {
        return PokemonDetailData()
    }

    private suspend fun loadPokemonDetail(id: Int) {
        viewData.loading.value = ShowLoading(true)
        pokemonRepository.pokemonDetail(id).collect { result ->
            viewData.loading.value = ShowLoading(false)
            result.either({ error -> }, {pokemon ->
                viewData.pokemon.value = pokemon
            })
        }
    }
}

class PokemonDetailData: MVVMIData() {
    var pokemon: MutableLiveData<Pokemon> = MutableLiveData()
}

class LoadPokemonDetail(val id: Int) : ModelFromViewInterface