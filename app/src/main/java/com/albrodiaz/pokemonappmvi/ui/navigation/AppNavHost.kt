package com.albrodiaz.pokemonappmvi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.albrodiaz.pokemonappmvi.ui.features.pokemondetail.PokemonDetail
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.MainScreenRoute.route) {
        composable(AppRoutes.MainScreenRoute.route) {
            PokemonScreen { name ->
                navController.navigate(AppRoutes.DetailScreenRoute.createRoute(name))
            }
        }
        composable(AppRoutes.DetailScreenRoute.route) { backstackEntry ->
            val argument = backstackEntry.arguments?.getString("id")
            PokemonDetail(pokemonName = argument.orEmpty())
        }
    }
}