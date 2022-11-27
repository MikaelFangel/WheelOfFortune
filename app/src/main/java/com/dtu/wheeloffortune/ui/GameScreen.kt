package com.dtu.wheeloffortune.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dtu.wheeloffortune.R
import com.dtu.wheeloffortune.ui.theme.WheelOfFortuneTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameScreenViewModel: GameScreenViewModel = viewModel(),
    onGameEnded: () -> Unit
) {
    val gameState by gameScreenViewModel.uiState.collectAsState()
    Log.d("Status", gameState.gameStatus.toString())
    if (gameState.gameStatus == GameCycle.Won || gameState.gameStatus == GameCycle.Lost)
        onGameEnded()

    Column {
        StatusLine(lives = gameState.remainingLives, score = gameState.userScore)
        Spacer(modifier = modifier.padding(20.dp))
        Row(
            Modifier
                .padding(
                    horizontal = 12.dp
                )
        ) {
            Text(
                text = stringResource(id = R.string.current_cat) +
                        ": ${gameState.currentCategory}"
            )
        }
        Word(guessedWord = gameState.guessedWord)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(id = R.string.current_letter_value) +
                        ": ${gameState.wheelScore}"
            )
        }
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { gameScreenViewModel.spinWheel() },
                        enabled = gameState.gameStatus == GameCycle.Spinning
                    ) {
                        Text(text = stringResource(id = R.string.spin_wheel))
                    }
                }
                Keys(
                    keys = gameState.isKeyGuessed,
                    enabled = gameState.gameStatus == GameCycle.Guessing
                ) {
                    gameScreenViewModel.keyPress(it)
                }
            }
        }
    }
}

@Composable
fun StatusLine(
    modifier: Modifier = Modifier,
    lives: Int,
    score: Int
) {
    val paddingValues = 12.dp

    Row {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(paddingValues)
        ) {
            Row {
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
    modifier: Modifier = Modifier,
    lives: Int
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
    modifier: Modifier = Modifier,
    score: Int
) {
    Text(text = stringResource(id = R.string.score) + ": $score")
}

@Composable
fun Word(
    modifier: Modifier = Modifier,
    guessedWord: String,
) {
    val lines = guessedWord.split("_")

    for (line in lines) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        )
        {
            line.forEach { c -> CharItem(c = c) }
        }
    }
}

@Composable
fun Keys(
    modifier: Modifier = Modifier,
    keys: SnapshotStateMap<Char, Boolean>,
    enabled: Boolean = true,
    onGuess: (Char) -> Unit
) {
    // TODO Find more generic way of solving this
    val ranges = listOf(
        'a'..'i',
        'j'..'q',
        'r'..'z',
    )
    for (i in 0 until 3) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            for (j in ranges[i])
                CharItem(c = j, enabled = keys[j] == true && enabled) {
                    onGuess(j)
                }
        }
    }
}

@Composable
fun CharItem(
    modifier: Modifier = Modifier,
    c: Char,
    enabled: Boolean = true,
    onGuess: () -> Unit = {},
) {
    Button(
        onClick = onGuess,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(3.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = MaterialTheme.colorScheme.primary,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = ShapeDefaults.Small,
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .padding(5.dp)
            .height(50.dp)
            .width(30.dp)
    ) {
        Text(text = c.uppercaseChar().toString(), fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    WheelOfFortuneTheme() {
        GameScreen(gameScreenViewModel = GameScreenViewModel()) {
            {}
        }
    }
}
