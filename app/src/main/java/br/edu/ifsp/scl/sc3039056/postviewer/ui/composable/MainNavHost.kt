package br.edu.ifsp.scl.sc3039056.postviewer.ui.composable

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import br.edu.ifsp.scl.sc3039056.postviewer.PostViewModel
import br.edu.ifsp.scl.sc3039056.postviewer.ui.navigation.Screen

@Composable
fun MainNavHost(
    mainNavHostController: NavHostController,
    postViewModel: PostViewModel,
    modifier: Modifier
) {
    NavHost(
        navController = mainNavHostController,
        startDestination = Screen.List.route
    ) {
        composable(route = Screen.List.route) {
            ListScreen(postViewModel = postViewModel, modifier = modifier) {
                mainNavHostController.navigate(Screen.Details.route)
            }
        }

        composable(route = Screen.Details.route) {
            DetailsScreen(postViewModel = postViewModel, modifier = modifier)
        }
    }
}
