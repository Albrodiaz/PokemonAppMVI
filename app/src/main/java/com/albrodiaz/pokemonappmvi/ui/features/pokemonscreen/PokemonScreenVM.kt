package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.domain.GetAllPokemonUseCase
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
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

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_LATEST
    )
    val event: Flow<Event> get() = _event

    init {
        viewModelScope.launch {
            getAllPokemonUseCase.invoke(size.value, offset.value).collect {
                _viewState.value = Success(it)
            }
            event.collect {
                Log.i("alberto", "event: $it")
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

                is PokemonScreenIntent.Navigate -> {
                    _event.tryEmit(Event.Navigate(route = route))
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
