package com.example.testcompose

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.example.testcompose.screens.PrettyUIScreen
import com.example.testcompose.screens.TestScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route ) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",      // "/{name}"    is passing an argument, non optional
            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "Philipp"
                    nullable = true
                }
            )
        ) {
            DetailScreen(name = it.arguments?.getString("name"))
        }
        composable(route = Screen.TestScreen.route) {
            TestScreen()
        }
        composable(route = Screen.PrettyUIScreen.route) {
            PrettyUIScreen()
        }
    }
}