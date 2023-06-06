package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import com.albrodiaz.pokemonappmvi.data.response.details.DetailResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonService: PokemonService) {

    val pokemon: Flow<List<Pokemon>> = callbackFlow {
        val data = pokemonService.getAllPokemons().body()?.mapToPokemon()
        trySend(data.orEmpty())
        awaitClose { data.orEmpty() }
    }

    fun getPokemonDetail(name: String): Flow<PokemonDetail> {
        return callbackFlow {
            val data = pokemonService.getPokemon(name).body()?.mapToPokeDetail()
            trySend(data ?: PokemonDetail("", emptyList()))
            awaitClose()
        }
    }
}

    private fun PokemonResponse.mapToPokemon(): List<Pokemon> {
        return this.results.map {
            Pokemon(it.name, it.url)
        }
    }

    private fun DetailResponse.mapToPokeDetail(): PokemonDetail {
        return PokemonDetail(name = name, abilities = abilities)
    }