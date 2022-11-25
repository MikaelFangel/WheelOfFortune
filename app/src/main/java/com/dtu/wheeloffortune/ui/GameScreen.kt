package com.dtu.wheeloffortune.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dtu.wheeloffortune.GameScreenViewModel
import com.dtu.wheeloffortune.R

class GameScreen {
    @Composable
    fun gameScreen(
        gameScreenViewModel: GameScreenViewModel = GameScreenViewModel(),
        modifier: Modifier = Modifier
    ) {

        val gameState by gameScreenViewModel.uiState.collectAsState()

        Column() {
            StatusLine(lives = gameState.remainingLives, score = gameState.userScore)
            Spacer(modifier = modifier.padding(20.dp))
            Word(onGuess = { /* TODO */ }, guessedWord = gameState.guessedWord /* TODO */)
            Keys(onGuess = { /* TODO */ }, keys = gameState.isKeyGuessed)
        }
    }
}

@Composable
fun StatusLine(
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
                UserLives(lives = lives)
            }
        }
        Column(modifier = modifier.padding(paddingValues)) {
            UserScore(score = score)
        }
    }
}

@Composable
fun UserLives(
    lives: Int,
    modifier: Modifier = Modifier
) {
    for (i in 0 until lives) {
        Icon(
            Icons.Rounded.Favorite,
            contentDescription = null,
            tint = Color(233, 30, 99, 255)
        )

    }
}

@Composable
fun UserScore(
    score: Int,
    modifier: Modifier = Modifier
) {
    Text(text = stringResource(id = R.string.score) + ": $score")
}

@Composable
fun Word(
    onGuess: (Char) -> Unit,
    guessedWord: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        guessedWord.forEach { c -> CharItem(c = c) }
    }
}

@Composable
fun Keys(
    onGuess: (Char) -> Unit,
    keys: HashMap<Char, Boolean>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        keys.forEach { (c, b) -> CharItem(c = c, enabled = b) }
    }
}

@Composable
fun CharItem(c: Char, enabled: Boolean = true) {
    Button(
        onClick = {},
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = ShapeDefaults.Small,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.padding(5.dp)
    ) {
        Text(text = c.uppercaseChar().toString(), fontWeight = FontWeight.Bold)
    }
}