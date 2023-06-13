package com.albrodiaz.pokemonappmvi.ui.features.searchscreen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.albrodiaz.pokemonappmvi.ui.components.SearchTextField

@Composable
fun SearchScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var value by remember { mutableStateOf("") }
        var expanded by remember { mutableStateOf(false) }
        val elements = listOf("Alberto", "Paloma", "Peaky", "Marley").filter { it.lowercase().contains(value.lowercase()) }

        SearchTextField(value = value) {
            expanded = it.isNotEmpty()
            value = it
        }
        CustomDropDownMenu(expanded = expanded) {
            elements.forEach {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Text(text = it)
                }
                Divider()
            }
        }
    }
}

@Composable
fun SearchRow() {
    Row(modifier = Modifier.fillMaxWidth()) {
        
    }
}

@Composable
fun CustomDropDownMenu(expanded: Boolean, rowContent: @Composable () -> Unit) {
    val height by animateFloatAsState(
        targetValue = if (expanded) .5f else 0f,
        animationSpec = tween(500)
    )

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(height)
    ) {
        Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(6.dp)) {
            rowContent()
        }
    }
}