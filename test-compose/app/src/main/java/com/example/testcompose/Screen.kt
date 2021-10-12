package com.example.testcompose

// here specify the single screens we have in the app

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")

    // helper function
    // this constructs a route with arguments together (only works with mandatory arguments, not optionals)
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}
