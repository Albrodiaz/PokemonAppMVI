package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.albrodiaz.pokemonappmvi.domain.GetDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailVM @Inject constructor(
    getDetailsUseCase: GetDetailsUseCase,
    savedstate: SavedStateHandle
) : ViewModel() {

    private val pokemonName = savedstate["name"] ?: ""

    val pokemon = getDetailsUseCase.invoke(pokemonName)

}