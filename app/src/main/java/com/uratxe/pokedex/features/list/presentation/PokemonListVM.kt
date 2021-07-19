package com.uratxe.pokedex.features.list.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.unatxe.commons.data.ApiError
import com.unatxe.commons.data.exceptions.Failure
import com.unatxe.commons.utils.runUI
import com.unatxe.mvvmi.MVVMIData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface
import com.unatxe.mvvmi.ShowLoading
import com.uratxe.pokedex.data.PokemonRepository
import com.uratxe.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.collect

class PokemonListVM(application: Application, private val repository: PokemonRepository)
    : MVVMIViewModel<PokemonListData>(application) {
    override fun initViewData(): PokemonListData {
        return PokemonListData()
    }

    override fun onViewInitialized() {
        runUI {
            getAllPokemons()
        }
    }

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        when(commands) {
            is AllPokemons -> getAllPokemons()
        }
    }

    private suspend fun getAllPokemons() {
        viewData.loading.value = ShowLoading(true)
        repository.allPokemons().collect {
            viewData.loading.value = ShowLoading(false)
            it.either({failure ->
                showError(failure)
            },{data ->
                viewData.pokemons.value = data
            })
        }
    }

    fun showError(failure: ApiError) {
        Log.e("Load ERROR", failure.toString())
    }
}

class PokemonListData: MVVMIData() {
    val pokemons = MutableLiveData<List<Pokemon>>()
}

object AllPokemons: ModelFromViewInterface