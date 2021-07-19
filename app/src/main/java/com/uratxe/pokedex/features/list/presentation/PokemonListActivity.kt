package com.uratxe.pokedex.features.list.presentation

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.uratxe.common.KoinProyectActivity
import com.uratxe.movetilt.R
import com.uratxe.movetilt.databinding.ActivityAnimeListBinding
import com.uratxe.pokedex.domain.Pokemon
import com.uratxe.pokedex.features.detail.presentation.PokemonDetailActivity
import kotlinx.android.synthetic.main.activity_anime_list.*
import kotlin.reflect.KClass

class PokemonListActivity: KoinProyectActivity<PokemonListVM, PokemonListData>() {
    private lateinit var binding: ActivityAnimeListBinding
    private val adapter by lazy { PokemonsAdapter(viewModel, onPokemonSelect) }

    override fun layoutId(): Int {
        return R.layout.activity_anime_list
    }

    override fun bindingView() {
        binding = ActivityAnimeListBinding.inflate(layoutInflater);
    }

    override fun getViewModelClass(): KClass<PokemonListVM> {
        return PokemonListVM::class
    }

    override fun setupViews() {
        aal_rv.layoutManager = LinearLayoutManager(this)
        aal_rv.adapter = adapter
    }

    override fun onModelInitialized(data: PokemonListData) {
        data.pokemons.observe(this) {
            adapter.loadData(it)
        }
    }

    private val onPokemonSelect = { pokemon: Pokemon ->
        val intent = Intent(this, PokemonDetailActivity::class.java).apply {
            putExtra(POKEMON_ID, pokemon.id)
        }
        //FIXME: startActivity(intent)
    }

    companion object {
        const val POKEMON_ID = "POKEMON_ID"
    }
}