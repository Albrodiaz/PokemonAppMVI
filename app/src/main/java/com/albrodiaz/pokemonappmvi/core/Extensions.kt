package com.albrodiaz.pokemonappmvi.core

import androidx.compose.foundation.lazy.grid.LazyGridState

//Strings
fun String.uppercaseFirst() =
    this.substring(0 until 1).uppercase() + this.substring(1 until this.length)

fun String.getIndex(): Int {
    val index = this
        .substring(this.length - 4 until this.length)
        .replace(Regex("[^0-9]"), "")
    return index.toInt()
}

//LazyGrid
fun LazyGridState.isScrolled() =
    layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
