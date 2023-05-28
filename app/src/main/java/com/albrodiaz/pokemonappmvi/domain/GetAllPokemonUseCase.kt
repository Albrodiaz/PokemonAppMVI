package com.albrodiaz.pokemonappmvi.domain

import com.albrodiaz.pokemonappmvi.data.PokemonService
import javax.inject.Inject

class GetAllPokemonUseCase @Inject constructor(private val pokemonService: PokemonService) {

    suspend operator fun invoke() = pokemonService.getAllPokemons().body()?.results
}