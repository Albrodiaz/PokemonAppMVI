package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

sealed class SearchScreenViewState {
    object Loading: SearchScreenViewState()
    data class Error(val error: String): SearchScreenViewState()
    data class Success(val names: List<String>): SearchScreenViewState()
}