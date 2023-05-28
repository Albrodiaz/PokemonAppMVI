package com.albrodiaz.pokemonappmvi.data

import com.albrodiaz.pokemonappmvi.data.response.PokemonResponse
import retrofit2.Response
import retrofit2.http.GET

interface PokemonService {

    @GET("pokemon?limit=151")
    suspend fun getAllPokemons(): Response<PokemonResponse>
}