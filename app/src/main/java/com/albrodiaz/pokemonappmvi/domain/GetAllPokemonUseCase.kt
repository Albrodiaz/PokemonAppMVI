package com.albrodiaz.pokemonappmvi.domain

import com.albrodiaz.pokemonappmvi.data.PokemonRepository
import com.albrodiaz.pokemonappmvi.data.response.Pokemon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(limit: Int, offset: Int): Flow<List<Pokemon>> = pokemonRepository.pokemon(limit, offset)
}