package com.albrodiaz.pokemonappmvi.ui.features.pokemondetail

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import com.albrodiaz.pokemonappmvi.ui.components.LoadingScreen
import com.albrodiaz.pokemonappmvi.ui.components.TypeIcon
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.uppercaseFirst
import com.albrodiaz.pokemonappmvi.ui.theme.Pink40
import com.albrodiaz.pokemonappmvi.ui.theme.Purple40

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
            is PokemonDetailViewState.Success -> DetailScreen(pokemon = data)
        }
    }
}

@Composable
private fun DetailScreen(pokemon: PokemonDetail) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(getColor(pokemon.type[0].type.name))
    ) {
        val (image, info, experience) = createRefs()

        Text(
            text = "Base experience: ${pokemon.baseExperience}", modifier = Modifier
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
            PokemonInfo(pokemonDetail = pokemon)
        }

        PokemonImage(
            image = pokemon.sprites,
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
private fun PokemonInfo(pokemonDetail: PokemonDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(120.dp))
        Text(
            text = pokemonDetail.name.uppercaseFirst(),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(50.dp))
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Weight", style = MaterialTheme.typography.bodyLarge)
                Text(text = "${pokemonDetail.weight}", style = MaterialTheme.typography.bodyLarge)
            }
            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(50.dp)
                    .padding(vertical = 8.dp)
            )

            pokemonDetail.type.forEach {
                PokemonType(
                    modifier = Modifier
                        .weight(1f),
                    type = it.type.name
                )
            }

            Divider(
                modifier = Modifier
                    .width(1.dp)
                    .height(50.dp)
                    .padding(vertical = 8.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Height", style = MaterialTheme.typography.bodyLarge)
                Text(text = "${pokemonDetail.height}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        pokemonDetail.abilities.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.ability.name.uppercaseFirst(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "${(1..100).random()}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
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
private fun InfoCard(modifier: Modifier, content: @Composable () -> Unit) {
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

@Composable
private fun PokemonType(modifier: Modifier = Modifier, type: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PokemonIcon(type = type)
        Text(text = type.uppercaseFirst())
    }
}

@Composable
private fun PokemonIcon(type: String) {
    Image(
        painter = painterResource(id = getDrawable(type)),
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape),
        contentDescription = null
    )
}

private fun getColor(type: String): Color {
    return when (type) {
        "water" -> Color.Cyan.copy(alpha = .3f)
        "fire", "dragon" -> Color.Red.copy(alpha = .5f)
        "grass" -> Color.Green.copy(alpha = .5f)
        "electric" -> Color.Yellow.copy(alpha = .5f)
        "poison" -> Purple40.copy(alpha = .5f)
        "fairy", "fighting" -> Pink40
        else -> Color.Gray.copy(alpha = .5f)
    }
}

private fun getDrawable(type: String): Int {
    return when (type) {
        "bug" -> TypeIcon.BUG.icon
        "dark" -> TypeIcon.DARK.icon
        "dragon" -> TypeIcon.DRAGON.icon
        "electric" -> TypeIcon.ELECTRIC.icon
        "fairy" -> TypeIcon.FAIRY.icon
        "fighting" -> TypeIcon.FIGHTING.icon
        "fire" -> TypeIcon.FIRE.icon
        "flying" -> TypeIcon.FLYING.icon
        "grass" -> TypeIcon.GRASS.icon
        "ground" -> TypeIcon.GROUND.icon
        "ice" -> TypeIcon.ICE.icon
        "normal" -> TypeIcon.NORMAL.icon
        "poison" -> TypeIcon.POISON.icon
        "psychic" -> TypeIcon.PSYCHIC.icon
        "rock" -> TypeIcon.ROCK.icon
        "steel" -> TypeIcon.STEEL.icon
        "water" -> TypeIcon.WATER.icon
        else -> TypeIcon.NORMAL.icon
    }
}