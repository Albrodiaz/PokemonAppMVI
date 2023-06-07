package com.albrodiaz.pokemonappmvi.data.response

import com.albrodiaz.pokemonappmvi.data.response.details.Ability

data class PokemonDetail(
    val name: String? = "",
    val background: String? = null,
    val abilities: List<Ability>? = emptyList(),
    val type: List<String>? = emptyList(),
    val sprites: String? = null,
    val baseExperience: Int? = 0
)