package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import com.albrodiaz.pokemonappmvi.data.response.details.DetailResponse
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.uppercaseFirst
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
            trySend(data ?: PokemonDetail())
            awaitClose()
        }
    }
}

private fun PokemonResponse.mapToPokemon(): List<Pokemon> =
    this.results.map {
        Pokemon(it.name, it.url)
    }

private fun DetailResponse.mapToPokeDetail(): PokemonDetail =
    PokemonDetail(
        name = name.uppercaseFirst(),
        background = types[0].type.name,
        abilities = abilities,
        type = types.map { it.type.name.uppercaseFirst() },
        sprites = sprites.front_default,
        baseExperience = base_experience
    )