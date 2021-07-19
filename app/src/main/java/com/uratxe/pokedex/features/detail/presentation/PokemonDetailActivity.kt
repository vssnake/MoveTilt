package com.uratxe.pokedex.features.detail.presentation

import com.unatxe.mvvmi.ModelFromViewInterface
import com.uratxe.common.KoinProyectActivity
import com.uratxe.movetilt.R
import com.uratxe.pokedex.features.list.presentation.PokemonListActivity.Companion.POKEMON_ID
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class PokemonDetailActivity: KoinProyectActivity<PokemonDetailVM, PokemonDetailData>() {
    private var pokemonID by Delegates.notNull<Int>()
    override fun layoutId(): Int {
        return R.layout.activity_pokemon_detail
    }

    override fun bindingView() {
    }

    override fun getViewModelClass(): KClass<PokemonDetailVM> {
        return PokemonDetailVM::class
    }

    override fun setupViews() {
        pokemonID = intent.getIntExtra(POKEMON_ID, 0)
    }

    override fun onModelInitialized(data: PokemonDetailData) {
        viewModel.launchEventFromView(LoadPokemonDetail)
    }
}