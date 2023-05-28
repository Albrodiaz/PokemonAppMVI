package com.albrodiaz.pokemonappmvi.data.response

data class PokemonResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Pokemon>
)
