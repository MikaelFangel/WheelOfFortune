package com.dtu.wheeloffortune

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dtu.wheeloffortune.ui.GameScreen
import com.dtu.wheeloffortune.ui.GameScreenViewModel
import com.dtu.wheeloffortune.ui.MainScreen

enum class Screens {
    MAIN_SCREEN,
    GAME_SCREEN,
}

@Composable
fun WheelOfFortuneNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val gameScreenViewModel = remember { GameScreenViewModel() }

    NavHost(
        navController = navController,
        startDestination = Screens.MAIN_SCREEN.name
    ) {
        composable(Screens.MAIN_SCREEN.name) {
            MainScreen {
                navController.navigate(Screens.GAME_SCREEN.name)
            }
        }
        composable(Screens.GAME_SCREEN.name) {
            GameScreen(gameScreenViewModel = gameScreenViewModel)
        }
    }
}