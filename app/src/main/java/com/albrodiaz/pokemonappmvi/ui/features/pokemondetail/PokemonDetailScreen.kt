package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import com.albrodiaz.pokemonappmvi.data.response.details.Type
import com.albrodiaz.pokemonappmvi.ui.components.LoadingScreen
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.uppercaseFirst

@Composable
fun PokemonDetailScreen(detailVM: PokemonDetailVM = hiltViewModel()) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val state by produceState<PokemonDetailViewState>(
        initialValue = PokemonDetailViewState.Loading,
        key1 = lifecycle,
        key2 = detailVM
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            detailVM.detailViewState.collect { value = it }
        }
    }

    with(state) {
        when (this) {
            is PokemonDetailViewState.Loading -> LoadingScreen()
            is PokemonDetailViewState.Error -> Text(text = error.message.toString())
            is PokemonDetailViewState.Success -> DetailScreen(data = data)
        }
    }
}

@Composable
fun DetailScreen(data: PokemonDetail) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(getColor(data.type[0].type.name))
    ) {
        val (image, info, experience) = createRefs()

        Text(
            text = "Base experience: ${data.baseExperience}", modifier = Modifier
                .padding(24.dp)
                .constrainAs(experience) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )

        InfoCard(
            modifier = Modifier.constrainAs(info) {
                bottom.linkTo(parent.bottom)
            }
        ) {
            PokemonInfo(
                name = data.name?.uppercaseFirst().orEmpty(),
                type = data.type
            )
        }
        PokemonImage(
            image = data.sprites.toString(),
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
fun PokemonInfo(name: String, type: List<Type>) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(120.dp))
        Text(
            text = name.uppercaseFirst(),
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth(.3f)
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            type.forEach {
                Column(
                    modifier = Modifier.height(150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.Info, contentDescription = null)
                    Text(text = it.type.name.uppercaseFirst())
                }
            }
        }
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
        "water" -> Color.Cyan.copy(alpha = .3f)
        "fire" -> Color.Red.copy(alpha = .5f)
        "grass" -> Color.Green.copy(alpha = .5f)
        else -> Color.Gray.copy(alpha = .5f)
    }
}