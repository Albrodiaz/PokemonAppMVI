package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.domain.GetDetailsUseCase
import com.albrodiaz.pokemonappmvi.ui.features.pokemondetail.PokemonDetailViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailVM @Inject constructor(
    getDetailsUseCase: GetDetailsUseCase,
    savedState: SavedStateHandle
) : ViewModel() {

    private val pokemonName = savedState["name"] ?: ""

    val detailViewState: StateFlow<PokemonDetailViewState> =
        getDetailsUseCase(pokemonName).map(::Success)
            .catch { PokemonDetailViewState.Error(it) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                PokemonDetailViewState.Loading
            )

}