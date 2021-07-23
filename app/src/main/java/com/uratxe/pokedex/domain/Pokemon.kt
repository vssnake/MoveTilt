package com.uratxe.pokedex.domain

import android.os.Parcelable
import com.unatxe.commons.utils.toInt
import com.uratxe.pokedex.data.dto.FeatureLinkDTO
import kotlinx.android.parcel.Parcelize

@Parcelize
class Pokemon(val id: Int, val name: String): Parcelable {
    val url = "https://pokeapi.co/api/v2/pokemon/$id"
    var isFavourite = false
    var hasDetail = false

    companion object{
        fun mapList(dtoList: List<FeatureLinkDTO>): List<Pokemon> {
            return dtoList.map { map(it) }
        }

        fun map(dto: FeatureLinkDTO): Pokemon {
            val id = dto.url.split("/").filterNot { it == "" }.last().toInt()
            val pkmn = Pokemon(id, dto.name)
            return pkmn
        }
    }
}

@Parcelize
enum class TypeSprite(val baseURL: String, val format: String): Parcelable {
    FRONT("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/",".png"),
    BACK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/",".png"),
    ARTWORK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/",".png"),
    UNNOFFICIAL_ARTWORK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/",".svg")
}