package com.dtu.wheeloffortune.ui

import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dtu.wheeloffortune.GameScreenViewModel

class GameScreen {
    @Composable
    fun gameScreen(
        gameScreenViewModel: GameScreenViewModel = GameScreenViewModel(),
        modifier: Modifier = Modifier
    ) {
        val gameState by gameScreenViewModel.uiState.collectAsState()
        Column() {
            statusLine(lives = gameState.remainingLives, score = gameState.userScore)
            word(onGuess = { /* TODO */ }, guessedWord = "" /* TODO */)
            keys(onGuess = { /* TODO */ })
        }
    }
}

@Composable
fun statusLine(
    lives: Int,
    score: Int,
    modifier: Modifier = Modifier
) {
    val paddingValues = 12.dp

    Row() {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(paddingValues)
        ) {
            Row() {
                userLives(lives = lives)
            }
        }
        Column(modifier = modifier.padding(paddingValues)) {
            userScore(score = score)
        }
    }
}

@Composable
fun userLives(
    lives: Int,
    modifier: Modifier = Modifier
) {
    for (i in 0 until lives) {
        Icon(
            Icons.Rounded.Favorite, contentDescription = null
        )

    }
}

@Composable
fun userScore(
    score: Int,
    modifier: Modifier = Modifier
) {
    Text(text = "Score: $score")
}

@Composable
fun word(
    onGuess: (Char) -> Unit,
    guessedWord: String,
    modifier: Modifier = Modifier
) {

}

@Composable
fun keys(
    onGuess: (Char) -> Unit,
    modifier: Modifier = Modifier
) {

}