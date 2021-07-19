package com.uratxe.pokedex.features.list.presentation

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.uratxe.animelist.features.list.OnMorePagesLoad
import com.uratxe.common.PaginationScrollListener
import com.uratxe.movetilt.R
import com.uratxe.pokedex.domain.Pokemon
import com.uratxe.pokedex.domain.TypeSprite
import kotlinx.android.synthetic.main.vh_pokemon_item.view.*
import java.util.*

class PokemonsAdapter(val pokemonListVM: PokemonListVM, val onPokemonSelect: (pokemon: Pokemon) -> Unit)
    : ListAdapter<Pokemon, PokemonsAdapter.PokemonItemVH>(DefaultItemCallback<Pokemon>(Pokemon::id)) {

    var isLoading = false
    var isLastPage = false

    internal fun loadData(dataSet : List<Pokemon>){
        isLoading = false
        val newResults = currentList + dataSet
        submitList(newResults)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(recyclerView) {
            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

            override fun loadMoreItems() {
                isLoading = true
                //you have to call loadmore items to get more data
                pokemonListVM.launchEventFromView(OnMorePagesLoad)
            }
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonItemVH {

        return PokemonItemVH(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.vh_pokemon_item,parent,false))
    }

    override fun onBindViewHolder(holder: PokemonItemVH, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PokemonItemVH(view : View) : RecyclerView.ViewHolder(view){
        fun bind(pokemon: Pokemon) {
            itemView.vhpi_title.text = pokemon.name.capitalize(Locale.ROOT)
            itemView.vhpi_id.text = "#${pokemon.id}"
            Glide.with(itemView.context).load("${TypeSprite.ARTWORK.baseURL}${pokemon.id}${TypeSprite.ARTWORK.format}").into(itemView.vhpi_image)
            itemView.vhpi_favourite.visibility = if(pokemon.isFavourite) View.VISIBLE else View.GONE
            itemView.setOnClickListener { onPokemonSelect(pokemon) }
        }
    }
}

class DefaultItemCallback<T>(private val idSelector: ((T) -> Any?)? = null) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return idSelector?.let { return it(oldItem) == it(newItem) } ?: oldItem == newItem
    }
    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean = oldItem == newItem
}

