package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonScreenVM @Inject constructor(getAllPokemonUseCase: GetAllPokemonUseCase) :
    ViewModel() {
    val viewState: StateFlow<PokemonScreenViewState> = getAllPokemonUseCase().map(::Success)
        .catch { PokemonScreenViewState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),
            PokemonScreenViewState.Loading
        )
}

sealed class PokemonScreenViewState {
    data class Error(val error: Throwable) : PokemonScreenViewState()
    data class Success(val data: List<Pokemon>) : PokemonScreenViewState()
    object Loading : PokemonScreenViewState()
}

