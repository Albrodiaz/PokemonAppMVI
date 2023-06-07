package com.albrodiaz.pokemonappmvi.data.response

import com.albrodiaz.pokemonappmvi.data.response.details.Ability
import com.albrodiaz.pokemonappmvi.data.response.details.Type

data class PokemonDetail(
    val id: Int = 0,
    val name: String = "",
    val abilities: List<Ability> = emptyList(),
    val type: List<Type> = emptyList(),
    val sprites: String = "",
    val baseExperience: Int = 0,
    val weight: Int = 0,
    val height: Int = 0
)