package com.uratxe.pokedex.data.dto

data class PokemonDTO(
    val id: Int,
    val name: String,
    val abilities: List<FeatureLinkDTO>
)