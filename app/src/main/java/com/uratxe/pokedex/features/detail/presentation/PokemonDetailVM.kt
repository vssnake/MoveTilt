package com.uratxe.pokedex.features.detail.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.unatxe.mvvmi.MVVMIData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface
import com.uratxe.pokedex.data.PokemonRepository
import com.uratxe.pokedex.domain.Pokemon

class PokemonDetailVM(aplication: Application, private val pokemonRepository: PokemonRepository)
    : MVVMIViewModel<PokemonDetailData>(aplication) {

    override fun onViewInitialized() {}

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        when(commands) {
            is LoadPokemonDetail -> loadPokemonDetail()
        }
    }

    override fun initViewData(): PokemonDetailData {
        return PokemonDetailData()
    }

    private suspend fun loadPokemonDetail() {
        pokemonRepository.pokemonDetail()
    }
}

class PokemonDetailData: MVVMIData() {
    val pokemon: MutableLiveData<Pokemon> = MutableLiveData()
}

object LoadPokemonDetail: ModelFromViewInterface