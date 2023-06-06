package com.albrodiaz.pokemonappmvi.ui.navigation

sealed class AppRoutes(val route: String) {
    object MainScreenRoute: AppRoutes("MainScreen")
    object DetailScreenRoute: AppRoutes("DetailScreen/{name}") {
        fun createRoute(name: String) = "DetailScreen/$name"
    }
}
