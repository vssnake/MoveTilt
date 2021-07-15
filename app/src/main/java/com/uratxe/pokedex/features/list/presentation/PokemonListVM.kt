package com.uratxe.pokedex.features.list.presentation

import android.app.Application
import com.unatxe.mvvmi.MVVMIData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface
import com.uratxe.pokedex.data.PokemonRepository
import com.uratxe.pokedex.data.PokemonService

class PokemonListVM(application: Application, private val repository: PokemonRepository)
    : MVVMIViewModel<PokemonListData>(application) {
    override fun onViewInitialized() {
        //repository.getPokemons().enqueue(object: Callback<List<PokemonDTO>>)
    }

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {

    }

    override fun initViewData(): PokemonListData {
        return PokemonListData()
    }
}

class PokemonListData: MVVMIData() {

}