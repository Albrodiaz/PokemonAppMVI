package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun AnimatedBottomFab(
    modifier: Modifier = Modifier,
    state: LazyGridState,
    visible: Boolean,
    icon: ImageVector = Icons.Default.Search,
    onClick: () -> Unit
) {

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
            RoundedFab(
                onClick = onClick,
                icon = icon
            )
        }
    }
}