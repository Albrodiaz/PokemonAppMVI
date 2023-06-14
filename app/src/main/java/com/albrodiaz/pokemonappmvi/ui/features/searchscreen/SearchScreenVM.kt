package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.albrodiaz.pokemonappmvi.domain.GetPokemonNamesUseCase
import com.albrodiaz.pokemonappmvi.ui.features.searchscreen.SearchScreenViewState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchScreenVM @Inject constructor(
    pokemonNamesUseCase: GetPokemonNamesUseCase
) : ViewModel() {

    val searchViewState: StateFlow<SearchScreenViewState> =
        pokemonNamesUseCase.invoke().map(::Success)
            .catch { SearchScreenViewState.Error(it.message ?: "") }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                SearchScreenViewState.Loading
            )
}