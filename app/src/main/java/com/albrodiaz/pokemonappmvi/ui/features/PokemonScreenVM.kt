package com.albrodiaz.pokemonappmvi.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import com.albrodiaz.pokemonappmvi.ui.features.MainScreenViewState.Success
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
    val viewState: StateFlow<MainScreenViewState> = getAllPokemonUseCase().map(::Success)
        .catch { MainScreenViewState.Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainScreenViewState.Loading)
}

sealed class MainScreenViewState {
    data class Error(val error: Throwable) : MainScreenViewState()
    data class Success(val data: List<Pokemon>) : MainScreenViewState()
    object Loading : MainScreenViewState()
}

