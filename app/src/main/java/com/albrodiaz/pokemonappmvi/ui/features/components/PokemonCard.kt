package com.albrodiaz.pokemonappmvi.ui.features.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.albrodiaz.pokemonappmvi.R

@Composable
fun PokemonCard(title: String, image: String, caught: Boolean) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(20.dp)
                    .padding(horizontal = 3.dp),
                horizontalArrangement = Arrangement.End
            ) {
                if (caught) {
                    Image(
                        painter = painterResource(id = R.drawable.pokemon_pokeball_png_image_hd),
                        contentDescription = null
                    )
                }
            }
            AsyncImage(
                model = image,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(125.dp),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    }
}