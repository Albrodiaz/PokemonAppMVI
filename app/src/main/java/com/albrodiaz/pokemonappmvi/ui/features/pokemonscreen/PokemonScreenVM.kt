package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonScreenVM @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase
) : ViewModel() {

    private val limit = MutableStateFlow(0)
    private val offset = MutableStateFlow(0)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _viewState =
        MutableStateFlow<PokemonScreenViewState>(PokemonScreenViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            limit.value += 10
            getAllPokemonUseCase.invoke(limit.value, offset.value).collect {
                _viewState.value = Success(it)
            }
        }
    }

    fun handle(action: PokemonScreenIntent) {
        with(action) {
            when (this) {
                is PokemonScreenIntent.LoadNext -> {
                    viewModelScope.launch {
                        _isLoading.value = true
                        delay(2000)
                        limit.value += 10
                        getAllPokemonUseCase.invoke(limit.value, offset.value).collect {
                            _viewState.value = Success(it)
                            _isLoading.value = false
                        }
                    }
                }
            }
        }
    }
}