package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.core.mapToPokeDetail
import com.albrodiaz.pokemonappmvi.data.response.PokemonDetail
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PokemonRepository @Inject constructor(private val pokemonService: PokemonService) {

    fun pokemon(limit: Int, offset: Int) = callbackFlow {
        val data = pokemonService.getAllPokemons(limit, offset).body()?.results
        trySend(data.orEmpty())
        awaitClose()
    }

    val pokemonNames = callbackFlow {
        val data =
            pokemonService.getPokemonNames()
                .body()?.results?.map { it.name }.orEmpty()
        trySend(data)
        awaitClose()
    }

    fun getPokemonDetail(name: String): Flow<PokemonDetail> {
        return callbackFlow {
            val data = pokemonService.getPokemon(name).body()?.mapToPokeDetail()
            trySend(data ?: PokemonDetail())
            awaitClose()
        }
    }
}