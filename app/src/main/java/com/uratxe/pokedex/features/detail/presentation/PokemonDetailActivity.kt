package com.uratxe.pokedex.features.detail.presentation

import androidx.viewbinding.ViewBinding
import com.unatxe.mvvmi.ModelFromViewInterface
import com.uratxe.common.KoinProyectActivity
import com.uratxe.movetilt.R
import com.uratxe.movetilt.databinding.ActivityPokemonDetailBinding
import com.uratxe.pokedex.features.list.presentation.PokemonListActivity.Companion.POKEMON_ID
import kotlin.properties.Delegates
import kotlin.reflect.KClass

class PokemonDetailActivity: KoinProyectActivity<PokemonDetailVM, PokemonDetailData>() {
    private lateinit var binding: ActivityPokemonDetailBinding
    private var pokemonID by Delegates.notNull<Int>()

    override fun inflateMainViewBinding(): ViewBinding {
        binding = ActivityPokemonDetailBinding.inflate(layoutInflater)
        return binding
    }

    override fun getViewModelClass(): KClass<PokemonDetailVM> {
        return PokemonDetailVM::class
    }

    override fun setupViews() {
        pokemonID = intent.getIntExtra(POKEMON_ID, 0)
        viewModel.launchEventFromView(LoadPokemonDetail(id = pokemonID))
    }

    override fun onModelInitialized(data: PokemonDetailData) {
    }
}