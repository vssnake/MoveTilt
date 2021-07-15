package com.uratxe.pokedex.domain

import com.unatxe.commons.utils.toInt
import com.uratxe.pokedex.data.dto.FeatureLinkDTO

class Pokemon(private val id: Int, private val name: String) {
    val url = "https://pokeapi.co/api/v2/pokemon/$id"

    companion object{
        fun map(dto: FeatureLinkDTO): Pokemon {
            val regex = Regex("/d+/")
            val id = dto.url.matches(regex).toInt()
            val pkmn = Pokemon(id, dto.name)
            return pkmn
        }
    }
}

enum class TypeSprite(val baseURL: String, val format: String) {
    FRONT("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/",".png"),
    BACK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/back/",".png"),
    ARTWORK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/",".png"),
    UNNOFFICIAL_ARTWORK("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/",".svg")
}