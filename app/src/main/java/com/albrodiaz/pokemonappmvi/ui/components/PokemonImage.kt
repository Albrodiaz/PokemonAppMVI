package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun PokemonImage(image: String, size: Dp = 125.dp) {
    AsyncImage(
        model = image,
        modifier = Modifier
            .size(size),
        contentDescription = null,
        contentScale = ContentScale.Fit
    )
}