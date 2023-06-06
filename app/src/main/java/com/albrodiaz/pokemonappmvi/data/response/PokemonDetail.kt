package com.albrodiaz.pokemonappmvi.data.response

import com.albrodiaz.pokemonappmvi.data.response.details.Ability

data class PokemonDetail(
    val name: String? = "",
    val abilities: List<Ability>? = emptyList()
)