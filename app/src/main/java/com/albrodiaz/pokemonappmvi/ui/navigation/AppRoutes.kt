package com.albrodiaz.pokemonappmvi.ui.navigation

sealed class AppRoutes(val route: String) {
    object MainScreenRoute: AppRoutes("MainScreen")
    object DetailScreenRoute: AppRoutes("DetailScreen/{id}") {
        fun createRoute(id: String) = "DetailScreen/$id"
        /*TODO: Check API to navigate to details*/
    }
}
