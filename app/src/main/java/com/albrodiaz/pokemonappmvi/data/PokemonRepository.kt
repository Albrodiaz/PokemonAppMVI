package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PokemonRepository @Inject constructor(pokemonService: PokemonService) {

    val pokemon: Flow<List<Pokemon>> = callbackFlow {
        val data = pokemonService.getAllPokemons().body()?.mapToPokemon()
        trySend(data.orEmpty())
        awaitClose { data.orEmpty() }
    }
}


private fun PokemonResponse.mapToPokemon(): List<Pokemon> {
    return this.results.map {
        Pokemon(it.name, it.url)
    }
}