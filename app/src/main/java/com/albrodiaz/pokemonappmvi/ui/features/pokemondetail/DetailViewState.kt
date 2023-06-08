package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail

sealed class PokemonDetailViewState {
    data class Error(val error: Throwable) : PokemonDetailViewState()
    data class Success(val data: PokemonDetail) : PokemonDetailViewState()
    object Loading : PokemonDetailViewState()
}