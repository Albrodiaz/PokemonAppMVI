package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.albrodiaz.pokemonappmvi.ui.theme.SpecialDarkGreen
import com.albrodiaz.pokemonappmvi.ui.theme.SpecialGreen

@Composable
fun RoundedFab(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    onClick: () -> Unit
) {
    FloatingActionButton(
        modifier = modifier.size(45.dp),
        shape = CircleShape,
        containerColor = SpecialDarkGreen,
        contentColor = SpecialGreen,
        onClick = { onClick() }
    ) {
        Surface(
            color = Color.Transparent,
            shape = CircleShape,
            modifier = modifier
                .border(1.dp, SpecialGreen, CircleShape)
                .padding(8.dp)
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = modifier.size(16.dp))
        }
    }
}