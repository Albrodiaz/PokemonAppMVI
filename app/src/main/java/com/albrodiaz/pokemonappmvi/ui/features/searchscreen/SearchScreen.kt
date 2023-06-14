package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.albrodiaz.pokemonappmvi.core.filterByName
import com.albrodiaz.pokemonappmvi.core.uppercaseFirst
import com.albrodiaz.pokemonappmvi.ui.components.CustomDropDownItem
import com.albrodiaz.pokemonappmvi.ui.components.CustomDropDownMenu
import com.albrodiaz.pokemonappmvi.ui.components.LoadingScreen
import com.albrodiaz.pokemonappmvi.ui.components.PokemonImage
import com.albrodiaz.pokemonappmvi.ui.components.SearchTextField
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.getPokemonImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    searchScreenVM: SearchScreenVM = hiltViewModel(),
    onPokemonSelected: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchViewState by searchScreenVM.searchViewState.collectAsState()
    var value by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

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
                    names = names,
                    onValueChange = {
                        value = it
                        expanded = it.isNotEmpty()
                    },
                    onTap = {
                        keyboardController?.hide()
                        expanded = false
                    },
                    onPokemonSelected = { onPokemonSelected(it) }
                )
            }
        }
    }
}

@Composable
fun SearchScreenContent(
    value: String,
    expanded: Boolean,
    names: List<String>,
    onTap: () -> Unit,
    onValueChange: (String) -> Unit,
    onPokemonSelected: (String) -> Unit
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
            val filteredList = names.filterByName(value)
            LazyColumn {
                items(filteredList, key = { it }) { name ->
                    val image = getPokemonImage(names.indexOf(name) + 1)
                    CustomDropDownItem(
                        headlineContent = { PokemonImage(image = image, size = 40.dp) },
                        bodyContent = { Text(text = name.uppercaseFirst()) },
                        onClickedItem = { onPokemonSelected(name) }
                    )
                }
            }
        }
    }
}