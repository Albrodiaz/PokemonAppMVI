package com.albrodiaz.pokemonappmvi.domain

import com.albrodiaz.pokemonappmvi.data.PokemonRepository
import javax.inject.Inject

class GetDetailsUseCase @Inject constructor(private val pokemonRepository: PokemonRepository) {

    operator fun invoke(name: String) = pokemonRepository.getPokemonDetail(name)

}