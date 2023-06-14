package com.albrodiaz.pokemonappmvi.core

import androidx.compose.foundation.lazy.grid.LazyGridState
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import com.albrodiaz.pokemonappmvi.data.response.details.DetailResponse

//Strings
fun String.uppercaseFirst() =
    this.substring(0 until 1).uppercase() + this.substring(1 until this.length)

fun String.getIndex(): Int {
    return substring(length - 4 until length)
        .replace(Regex("[^0-9]"), "")
        .toInt()
}

fun List<String>.filterByName(text: String) =
    this.filter { it.lowercase().contains(text.lowercase()) }

//LazyGrid
fun LazyGridState.isScrolled() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1

//Response
fun PokemonResponse.mapToPokemon(): List<Pokemon> =
    this.results.map {
        Pokemon(it.name, it.url)
    }

fun DetailResponse.mapToPokeDetail(): PokemonDetail =
    PokemonDetail(
        id = id,
        name = name,
        abilities = abilities,
        type = types,
        sprites = sprites,
        baseExperience = base_experience,
        weight = weight,
        height = height,
        stats = stats
    )