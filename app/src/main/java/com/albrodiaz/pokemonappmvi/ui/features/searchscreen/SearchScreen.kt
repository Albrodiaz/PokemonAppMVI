package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.albrodiaz.pokemonappmvi.core.uppercaseFirst
import com.albrodiaz.pokemonappmvi.ui.components.CustomDropDownItem
import com.albrodiaz.pokemonappmvi.ui.components.CustomDropDownMenu
import com.albrodiaz.pokemonappmvi.ui.components.LoadingScreen
import com.albrodiaz.pokemonappmvi.ui.components.PokemonImage
import com.albrodiaz.pokemonappmvi.ui.components.RoundedFab
import com.albrodiaz.pokemonappmvi.ui.components.SearchTextField

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    searchScreenVM: SearchScreenVM = hiltViewModel(),
    onClose: () -> Unit,
    onPokemonSelected: (String) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchViewState by produceState<SearchScreenViewState>(
        initialValue = SearchScreenViewState.Loading,
        key1 = lifecycle,
        key2 = searchScreenVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            searchScreenVM.searchViewState.collect { viewState -> value = viewState }
        }
    }
    var value by remember { mutableStateOf("") }
    val expanded by remember(value) {
        derivedStateOf { value.isNotEmpty() }
    }

    with(searchViewState) {
        when (this) {
            is SearchScreenViewState.Loading -> {
                LoadingScreen()
            }

            is SearchScreenViewState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f), contentAlignment = Alignment.Center
                ) {
                    Text(text = error)
                }
            }

            is SearchScreenViewState.Success -> {
                SearchScreenContent(
                    value = value,
                    expanded = expanded,
                    pokemons = pokemons,
                    onValueChange = {
                        value = it
                    },
                    onTap = {
                        keyboardController?.hide()
                    },
                    onPokemonSelected = { onPokemonSelected(it) },
                    onClose = onClose
                )
            }
        }
    }
}

@Composable
fun SearchScreenContent(
    value: String,
    expanded: Boolean,
    pokemons: List<SearchablePokemonItem>,
    onTap: () -> Unit,
    onValueChange: (String) -> Unit,
    onPokemonSelected: (String) -> Unit,
    onClose: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures { onTap() }
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchTextField(value = value, onValueChange = onValueChange)

        CustomDropDownMenu(expanded = expanded) {
            val filteredList = pokemons.filter { it.name.lowercase().contains(value.lowercase()) }
            LazyColumn {
                items(filteredList, key = { it.name }) {
                    CustomDropDownItem(
                        headlineContent = { PokemonImage(image = it.image, size = 40.dp) },
                        bodyContent = { Text(text = it.name.uppercaseFirst()) },
                        onClickedItem = { onPokemonSelected(it.name) }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 8.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            RoundedFab(icon = Icons.Filled.Close, onClick = onClose)
        }
    }
}