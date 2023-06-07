package com.albrodiaz.pokemonappmvi.data.response

import com.albrodiaz.pokemonappmvi.data.response.details.Ability
import com.albrodiaz.pokemonappmvi.data.response.details.Type

data class PokemonDetail(
    val name: String? = "",
    val abilities: List<Ability>? = emptyList(),
    val type: List<Type> = emptyList(),
    val sprites: String? = null,
    val baseExperience: Int? = 0
)