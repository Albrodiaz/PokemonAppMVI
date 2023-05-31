package com.albrodiaz.pokemonappmvi.ui.features

import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle

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
    val interactionSource = remember { MutableInteractionSource() }

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
                    LazyColumn {
                        items(data) {
                            Text(text = it.name, modifier = Modifier
                                .clickable { }
                                .hoverable(
                                    interactionSource = interactionSource,
                                    enabled = true
                                )
                            )
                        }
                    }
                }
            }
        }
    }

}