package com.albrodiaz.pokemonappmvi.ui.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonScreenVM @Inject constructor(private val getAllPokemonUseCase: GetAllPokemonUseCase) :
    ViewModel() {

    private val _viewState = MutableStateFlow<MainScreenViewState>(MainScreenViewState.Loading)
    val viewState: StateFlow<MainScreenViewState> get() = _viewState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (getAllPokemonUseCase.invoke().isNullOrEmpty()) {
                _viewState.value = MainScreenViewState.Error(Throwable("Error al cargar los datos"))
            } else {
                _viewState.value =
                    MainScreenViewState.Success(getAllPokemonUseCase.invoke() ?: emptyList())
            }
        }
    }
}

sealed class MainScreenViewState {
    data class Error(val error: Throwable) : MainScreenViewState()
    data class Success(val data: List<Pokemon>) : MainScreenViewState()
    object Loading : MainScreenViewState()
}

