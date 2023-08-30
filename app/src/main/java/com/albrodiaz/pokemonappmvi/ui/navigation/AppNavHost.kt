package com.albrodiaz.pokemonappmvi.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.albrodiaz.pokemonappmvi.ui.features.searchscreen.SearchScreen
import com.albrodiaz.pokemonappmvi.ui.features.pokemondetail.PokemonDetailScreen
import com.albrodiaz.pokemonappmvi.ui.features.pokemonscreen.PokemonScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppRoutes.MainScreenRoute.route) {
        composable(AppRoutes.MainScreenRoute.route) {
            PokemonScreen { destination ->
                navController.navigate(destination)
            }
        }
        composable(
            AppRoutes.DetailScreenRoute.route,
            arguments = listOf(navArgument("name") { type = NavType.StringType })
        ) { backstackEntry ->
            backstackEntry.arguments?.getString("name")
            PokemonDetailScreen(navigateBack = {
                navController.navigateUp()
            })
        }
        composable(AppRoutes.SearchScreen.route) {
            SearchScreen(onClose = {
                navController.popBackStack(
                    AppRoutes.MainScreenRoute.route,
                    inclusive = false
                )
            }) { destination ->
                navController.navigate(AppRoutes.DetailScreenRoute.createRoute(destination))
            }
        }
    }
}