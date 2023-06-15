package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

sealed class SearchScreenViewState {
    object Loading: SearchScreenViewState()
    data class Error(val error: String): SearchScreenViewState()
    data class Success(val pokemons: List<SearchablePokemonItem>): SearchScreenViewState()
}

data class SearchablePokemonItem(
    val name: String,
    val image: String
)