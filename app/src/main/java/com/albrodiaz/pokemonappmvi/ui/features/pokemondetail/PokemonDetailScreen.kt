package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail

@Composable
fun PokemonDetailScreen(detailVM: PokemonDetailVM = hiltViewModel()) {
    val pokemon by detailVM.pokemon.collectAsState(PokemonDetail())

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = getColor(pokemon.background.orEmpty()))
    ) {
        val (image, info, experience) = createRefs()

        Text(text = "Base experience: ${pokemon.baseExperience}", modifier = Modifier.padding(24.dp).constrainAs(experience) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        })

        InfoCard(
            modifier = Modifier.constrainAs(info) {
                bottom.linkTo(parent.bottom)
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(100.dp))
                Text(
                    text = pokemon.name ?: "", style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(50.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(.3f)
                        .align(Alignment.CenterHorizontally),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    pokemon.type?.forEach {
                        Text(text = it, style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        PokemonImage(
            image = pokemon.sprites.toString(),
            modifier = Modifier.constrainAs(image) {
                top.linkTo(info.top)
                bottom.linkTo(info.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

    }
}

@Composable
private fun PokemonImage(image: String, modifier: Modifier) {
    AsyncImage(
        model = image,
        contentScale = ContentScale.Crop,
        contentDescription = null,
        modifier = modifier.size(200.dp)
    )
}

@Composable
fun InfoCard(modifier: Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier
            .fillMaxHeight(.7f)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        content()
    }
}

private fun getColor(type: String): Color {
    return when (type) {
        "fire" -> Color.Red
        "water" -> Color.Cyan
        "grass" -> Color.Green
        else -> Color.Gray
    }
}