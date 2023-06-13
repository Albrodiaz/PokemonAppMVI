package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonScreenVM @Inject constructor(
    private val getAllPokemonUseCase: GetAllPokemonUseCase
) : ViewModel() {

    private val size = MutableStateFlow(10)
    private val offset = MutableStateFlow(0)

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _viewState =
        MutableStateFlow<PokemonScreenViewState>(PokemonScreenViewState.Loading)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllPokemonUseCase.invoke(size.value, offset.value).collect {
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
                        offset.value += size.value
                        getAllPokemonUseCase.invoke(size.value, offset.value).collect {
                            _viewState.value = Success(_viewState.value.pokemons + it)
                            _isLoading.value = false
                        }
                    }
                }
            }
        }
    }
}

val PokemonScreenViewState.pokemons: List<Pokemon>
    get() = when (this) {
        is Success -> data
        else -> emptyList()
    }
