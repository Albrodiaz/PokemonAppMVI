package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.albrodiaz.pokemonappmvi.R
import com.albrodiaz.pokemonappmvi.core.getIndex
import com.albrodiaz.pokemonappmvi.core.isScrolled
import com.albrodiaz.pokemonappmvi.core.uppercaseFirst
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.ui.components.AnimatedBottomFab
import com.albrodiaz.pokemonappmvi.ui.components.PokemonCard

@Composable
fun PokemonScreen(pokemonVM: PokemonScreenVM = hiltViewModel(), selectedPokemon: (String) -> Unit) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val listState = rememberLazyGridState()
    val isLoading by pokemonVM.isLoading.collectAsState()
    val state by produceState<PokemonScreenViewState>(
        initialValue = PokemonScreenViewState.Loading,
        key1 = lifecycle,
        key2 = pokemonVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            pokemonVM.viewState.collect { viewState -> value = viewState }
        }
    }

    Column(Modifier.fillMaxSize()) {
        with(state) {
            when (this) {
                is PokemonScreenViewState.Loading -> {
                    val composition by
                    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.pokeball_loading))
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = Color.Gray.copy(alpha = .1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        LottieAnimation(
                            modifier = Modifier.size(120.dp),
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )
                    }
                }

                is PokemonScreenViewState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .zIndex(1f), contentAlignment = Alignment.Center
                    ) {
                        Text(text = error.message.toString())
                    }
                }

                is PokemonScreenViewState.Success -> {
                    PokemonScreenContent(
                        listState = listState,
                        data = data,
                        isLoading = isLoading,
                        onScroll = { pokemonVM.handle(PokemonScreenIntent.LoadNext) }
                    ) { selected ->
                        selectedPokemon(selected)
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonScreenContent(
    listState: LazyGridState,
    data: List<Pokemon>,
    isLoading: Boolean,
    onScroll: () -> Unit,
    onSelected: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            modifier = Modifier.align(Alignment.Center),
            columns = GridCells.Fixed(2),
            state = listState
        ) {
            items(data) { pokemon ->
                val index = pokemon.url.getIndex()
                PokemonCard(
                    title = pokemon.name.uppercaseFirst(),
                    image = getPokemonImage(index = index)
                ) {
                    onSelected(pokemon.name)
                }
            }
        }
        if (isLoading) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        AnimatedBottomFab(
            modifier = Modifier.align(Alignment.BottomCenter),
            state = listState
        ) {
            //TODO: SearchScreen
        }

        if (listState.isScrolled()) {
            LaunchedEffect(Unit) {
                onScroll()
            }
        }
    }
}

private fun getPokemonImage(index: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index}.png"

