package com.albrodiaz.pokemonappmvi.domain

import com.albrodiaz.pokemonappmvi.data.PokemonRepository
import javax.inject.Inject

class GetPokemonNamesUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    operator fun invoke() = pokemonRepository.pokemonNames

}