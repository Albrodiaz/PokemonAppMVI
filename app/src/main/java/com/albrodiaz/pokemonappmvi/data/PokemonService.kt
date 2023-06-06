package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.data.response.details.DetailResponse
import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon?limit=151")
    suspend fun getAllPokemons(): Response<PokemonResponse>

    @GET("{pokemon}")
    suspend fun getPokemon(@Path("pokemon") pokemon: String): Response<DetailResponse>
}