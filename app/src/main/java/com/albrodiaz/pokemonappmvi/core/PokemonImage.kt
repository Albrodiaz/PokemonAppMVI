package com.albrodiaz.pokemonappmvi.core

/**
 La API, al acceder a las imágenes de official-artwork transforma el índice 1010 en 10001.
 Con esta función ajustamos el índice para que muestre la imágen correcta
*/

fun getPokemonImage(index: Int) =
    if (index > 1010) {
        var tempIndex = index
        tempIndex += 8990
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${tempIndex}.png"
    } else {
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${index}.png"
    }