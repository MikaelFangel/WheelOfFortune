package com.dtu.wheeloffortune

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dtu.wheeloffortune.ui.GameScreen
import com.dtu.wheeloffortune.ui.GameScreenViewModel
import com.dtu.wheeloffortune.ui.MainScreen

enum class Screens(screen: String) {
    MainScreen("Main Scree"),
    GameScreen("Game Screen"),
}

@Composable
fun WheelOfFortuneNavHost(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screens.MainScreen.name
    ) {
        composable(Screens.MainScreen.name) {
            MainScreen {
                navController.navigate(Screens.GameScreen.name)
            }
        }
        composable(Screens.GameScreen.name) {
            GameScreen(gameScreenViewModel = GameScreenViewModel())
        }
    }
}