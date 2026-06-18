package br.edu.ifsp.scl.sc3039056.postviewer.ui.navigation

sealed class Screen(val route: String) {
    object List : Screen(route = "list")
    object Details : Screen(route = "details")
}
