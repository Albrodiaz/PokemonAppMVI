package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import com.albrodiaz.pokemonappmvi.data.response.Pokemon

sealed class PokemonScreenViewState {
    data class Error(val error: Throwable) : PokemonScreenViewState()
    data class Success(val data: List<Pokemon>) : PokemonScreenViewState()
    object Loading : PokemonScreenViewState()
}

sealed class PokemonScreenIntent {
    object LoadNext: PokemonScreenIntent()
    data class Navigate(val route: String): PokemonScreenIntent()
}

sealed class Event {
    object Idle : Event()
    data class Navigate(val route: String) : Event()
}