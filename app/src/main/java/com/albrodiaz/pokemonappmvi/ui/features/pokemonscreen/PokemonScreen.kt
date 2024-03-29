package com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.albrodiaz.pokemonappmvi.core.getIndex
import com.albrodiaz.pokemonappmvi.core.getPokemonImage
import com.albrodiaz.pokemonappmvi.core.isScrolled
import com.albrodiaz.pokemonappmvi.core.uppercaseFirst
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.ui.components.AnimatedBottomFab
import com.albrodiaz.pokemonappmvi.ui.components.LoadingScreen
import com.albrodiaz.pokemonappmvi.ui.components.PokemonCard
import com.albrodiaz.pokemonappmvi.ui.navigation.AppRoutes
import kotlinx.coroutines.launch

@Composable
fun PokemonScreen(
    pokemonVM: PokemonScreenVM = hiltViewModel(),
    navigateTo: (String) -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val listState = rememberLazyGridState()
    val isLoading by pokemonVM.isLoading.collectAsState()
    val viewState by produceState<PokemonScreenViewState>(
        initialValue = PokemonScreenViewState.Loading,
        key1 = lifecycle,
        key2 = pokemonVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            pokemonVM.viewState.collect { viewState -> value = viewState }
        }
    }
    val event by pokemonVM.event.collectAsState(initial = Event.Idle)

    LaunchedEffect(event) {
        with(event) {
            when (this) {
                is Event.Idle -> Unit
                is Event.Navigate -> navigateTo(route)
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        with(viewState) {
            when (this) {
                is PokemonScreenViewState.Loading -> {
                    LoadingScreen()
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
                        onSearchClick = {
                            pokemonVM.handle(PokemonScreenIntent.Navigate(AppRoutes.SearchScreen.route))
                        },
                        onScroll = { pokemonVM.handle(PokemonScreenIntent.LoadNext) }
                    ) { selected ->
                        pokemonVM.handle(
                            PokemonScreenIntent.Navigate(
                                AppRoutes.DetailScreenRoute.createRoute(selected)
                            )
                        )
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
    onSearchClick: () -> Unit,
    onSelected: (String) -> Unit
) {
    val scope = rememberCoroutineScope()

    val searchVisible by remember(listState) {
        derivedStateOf { listState.firstVisibleItemIndex <= 1 }
    }

    val upVisible by remember(listState) {
        derivedStateOf { listState.firstVisibleItemIndex >= 25 }
    }

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
            modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp),
            state = listState,
            icon = Icons.Filled.Search,
            visible = searchVisible
        ) {
            onSearchClick()
        }

        AnimatedBottomFab(
            modifier = Modifier.align(Alignment.BottomCenter).padding(24.dp),
            state = listState,
            icon = Icons.Default.KeyboardArrowUp,
            visible = upVisible
        ) {
            scope.launch {
                listState.animateScrollToItem(0)
            }
        }

        if (listState.isScrolled()) {
            LaunchedEffect(Unit) {
                onScroll()
            }
        }
    }
}

