package com.dtu.wheeloffortune.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dtu.wheeloffortune.R
import com.dtu.wheeloffortune.ui.theme.WheelOfFortuneTheme

@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameScreenViewModel: GameScreenViewModel,
    finishApp: () -> Unit
) {
    val gameState = gameScreenViewModel.uiState
    if (gameState.gameStatus == GameCycle.WON || gameState.gameStatus == GameCycle.LOST)
        GameEndedDialog(gameState = gameState, finishApp = { finishApp() }) {
            gameScreenViewModel.resetGame()
        }

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
                text = stringResource(
                    id = if (gameState.wheelScore != 0)
                        R.string.current_letter_value
                    else
                        R.string.bankrupt
                ) + ": ${gameState.wheelScore}"
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
                        enabled = gameState.gameStatus == GameCycle.SPINNING
                    ) {
                        Text(text = stringResource(id = R.string.spin_wheel))
                    }
                }
                Keys(
                    keys = gameState.isKeyGuessed,
                    enabled = gameState.gameStatus == GameCycle.GUESSING
                ) {
                    gameScreenViewModel.keyPress(it)
                }
            }
        }
    }
}

@Composable
fun GameEndedDialog(
    gameState: GameScreenUiState,
    finishApp: () -> Unit,
    playAgain: () -> Unit
) = AlertDialog(
    title = {
        if (gameState.gameStatus == GameCycle.WON)
            Text(text = stringResource(id = R.string.game_won))
        else
            Text(text = stringResource(id = R.string.game_lost))
    },
    text = {
        Text(
            text =
            stringResource(id = R.string.your_score) + ": ${gameState.userScore}\n" +
                    stringResource(id = R.string.the_cat) + ": ${gameState.currentCategory}\n" +
                    stringResource(id = R.string.the_word) + ": ${gameState.currentWord}"
        )
    },
    onDismissRequest = {},
    dismissButton = {
        OutlinedButton(onClick = { finishApp() }) {
            Text(text = stringResource(id = R.string.end_game))
        }
    },
    confirmButton = {
        Button(onClick = playAgain) {
            Text(text = stringResource(id = R.string.play_again))
        }
    }
)

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
fun UserScore(modifier: Modifier = Modifier, score: Int) =
    Text(text = stringResource(id = R.string.score) + ": $score")

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
    Surface() {
        WheelOfFortuneTheme {
            GameScreen(gameScreenViewModel = GameScreenViewModel()) {}
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GameScreenPreviewDarkMode() {
    WheelOfFortuneTheme {
        Surface() {
            GameScreen(gameScreenViewModel = GameScreenViewModel()) {}
        }
    }
}
