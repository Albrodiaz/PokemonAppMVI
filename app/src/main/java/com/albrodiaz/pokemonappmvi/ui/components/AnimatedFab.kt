package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedBottomFab(modifier: Modifier = Modifier, state: LazyGridState, onClick: () -> Unit) {
    val visible by remember(state) {
        derivedStateOf { state.firstVisibleItemIndex <= 1 }
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically(initialOffsetY = { state.layoutInfo.viewportSize.height }),
            exit = slideOutVertically(
                animationSpec = tween(1000),
                targetOffsetY = { state.layoutInfo.viewportSize.height }
            )
        ) {
            FloatingActionButton(
                onClick = onClick,
                modifier = modifier
                    .padding(24.dp),
                shape = CircleShape
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    }
}