package com.uratxe.pokedex.features.detail.presentation

import android.app.Application
import com.unatxe.mvvmi.MVVMIData
import com.unatxe.mvvmi.MVVMIViewModel
import com.unatxe.mvvmi.ModelFromViewInterface

class PokemonDetailVM(aplication: Application): MVVMIViewModel<PokemonDetailData>(aplication) {
    override fun onViewInitialized() {
        TODO("Not yet implemented")
    }

    override suspend fun onEventFromView(commands: ModelFromViewInterface) {
        TODO("Not yet implemented")
    }

    override fun initViewData(): PokemonDetailData {
        TODO("Not yet implemented")
    }
}

class PokemonDetailData: MVVMIData() {

}