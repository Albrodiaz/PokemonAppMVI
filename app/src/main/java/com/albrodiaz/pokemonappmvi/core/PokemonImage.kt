package com.albrodiaz.pokemonappmvi.core

fun getPokemonImage(index: Int) =
    if (index > 1010) {
        var tempIndex = index
        tempIndex += 8990
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${tempIndex}.png"
    } else {
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index}.png"
    }