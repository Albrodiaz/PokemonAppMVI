package com.albrodiaz.pokemonappmvi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.albrodiaz.pokemonappmvi.R

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    val composition by
    rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.pokeball_loading))
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Gray.copy(alpha = .3f)),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            modifier = Modifier.size(120.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever
        )
    }
}