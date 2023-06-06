package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail

@Composable
fun PokemonDetailScreen(pokemonName: String, detailVM: PokemonDetailVM = hiltViewModel()) {
    val pokemon by detailVM.pokemon.collectAsState(PokemonDetail())

    /*TODO: Create layout with image, abilities, type and necessary data*/

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = pokemon.sprites.toString(), contentDescription = "", modifier = Modifier.size(200.dp))
        Text(text = pokemon.name.orEmpty())
        LazyColumn {
            items(pokemon.abilities.orEmpty()) {
                Text(text = it.ability.name)
            }
            items(pokemon.type.orEmpty()) {
                Text(text = it.type.name)
            }
        }
    }
}