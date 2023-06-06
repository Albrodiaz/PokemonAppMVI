package com.albrodiaz.pokemonappmvi.ui.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.albrodiaz.pokemonappmvi.ui.features.components.PokemonCard

@Composable
fun MainScreen(pokemonVM: PokemonScreenVM = hiltViewModel()) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by produceState<MainScreenViewState>(
        initialValue = MainScreenViewState.Loading,
        key1 = lifecycle,
        key2 = pokemonVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            pokemonVM.viewState.collect { value = it }
        }
    }

    Column(Modifier.fillMaxSize()) {
        with(state) {
            when (this) {
                is MainScreenViewState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(modifier = Modifier.size(100.dp))
                    }
                }

                is MainScreenViewState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = error.message.toString())
                    }
                }

                is MainScreenViewState.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(data) { pokemon ->
                            val index = data.indexOf(pokemon)
                            PokemonCard(
                                title = pokemon.name.uppercaseFirst(),
                                image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${index + 1}.png"
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun String.uppercaseFirst() =
    this.substring(0 until 1).uppercase() + this.substring(1 until this.length)