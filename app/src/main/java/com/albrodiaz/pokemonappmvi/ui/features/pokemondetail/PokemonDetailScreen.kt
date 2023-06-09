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
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.albrodiaz.pokemonappmvi.ui.components.icon
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.uppercaseFirst
import com.albrodiaz.pokemonappmvi.ui.theme.Pink40
import com.albrodiaz.pokemonappmvi.ui.theme.Purple40
import com.albrodiaz.pokemonappmvi.ui.theme.SpecialGreen

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
    var shiny by remember { mutableStateOf(false) }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(getColor(pokemon.type[0].type.name))
    ) {
        val (image, info, experience, experienceBar) = createRefs()

        PCSlider(
            pc = pokemon.baseExperience.toFloat(),
            modifier = Modifier.constrainAs(experienceBar) {
                top.linkTo(experience.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )

        PCText(
            value = pokemon.baseExperience,
            modifier = Modifier.constrainAs(experience) {
                top.linkTo(parent.top, margin = 1.dp)
                bottom.linkTo(experienceBar.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

        InfoCard(
            modifier = Modifier.constrainAs(info) {
                bottom.linkTo(parent.bottom)
            }
        ) {
            PokemonInfo(pokemonDetail = pokemon, checked = shiny) {
                shiny = it
            }
        }

        pokemon.sprites?.let {
            PokemonImage(
                image = if (shiny) it.other.home.front_shiny else it.other.home.front_default,
                modifier = Modifier.constrainAs(image) {
                    top.linkTo(experience.bottom, margin = 100.dp)
                    bottom.linkTo(info.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
private fun PCSlider(pc: Float, modifier: Modifier = Modifier) {
    Slider(
        value = pc,
        onValueChange = { },
        valueRange = 1f..400f,
        enabled = false,
        colors = SliderDefaults.colors(
            disabledThumbColor = Color.White,
            disabledActiveTrackColor = Color.White
        ),
        modifier = modifier
            .padding(horizontal = 32.dp)
    )
}

@Composable
private fun PCText(value: Int, modifier: Modifier = Modifier) {
    Text(
        text = "Pc ${value}/400",
        style = MaterialTheme.typography.titleMedium.copy(
            color = Color.White,
            fontWeight = FontWeight.Black
        ),
        modifier = modifier
            .padding(horizontal = 24.dp)
    )
}

@Composable
private fun PokemonInfo(
    pokemonDetail: PokemonDetail,
    checked: Boolean = false,
    onChecked: (Boolean) -> Unit
) {
    val healthPoints = pokemonDetail.stats[0].base_stat

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(60.dp))
        Text(
            text = pokemonDetail.name.uppercaseFirst(),
            style = MaterialTheme.typography.headlineMedium
        )
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HealthRow(healthPoints = healthPoints)
        }
        Spacer(modifier = Modifier.height(20.dp))
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
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = it.ability.name.uppercaseFirst(),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "${75}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Switch(
                checked = checked,
                onCheckedChange = {
                    onChecked(it)
                },
                modifier = Modifier.padding(end = 12.dp)
            )
            Text(text = if (checked) "Normal" else "Shiny")
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
private fun HealthRow(healthPoints: Int) {
    Slider(
        modifier = Modifier.fillMaxWidth(.6f),
        value = healthPoints.toFloat(),
        onValueChange = {},
        enabled = false,
        valueRange = 1f..healthPoints.toFloat(),
        colors = SliderDefaults.colors(
            disabledThumbColor = Color.Transparent,
            disabledActiveTrackColor = SpecialGreen
        )
    )
    Text(text = "$healthPoints/$healthPoints HP", style = MaterialTheme.typography.bodyMedium)
}

@Composable
private fun PokemonType(modifier: Modifier = Modifier, type: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TypeIcon(type = type)
        Text(text = type.uppercaseFirst())
    }
}

@Composable
private fun TypeIcon(type: String) {
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
        "poison" -> TypeIcon.POISON.icon
        "psychic" -> TypeIcon.PSYCHIC.icon
        "rock" -> TypeIcon.ROCK.icon
        "steel" -> TypeIcon.STEEL.icon
        "water" -> TypeIcon.WATER.icon
        else -> TypeIcon.NORMAL.icon
    }
}