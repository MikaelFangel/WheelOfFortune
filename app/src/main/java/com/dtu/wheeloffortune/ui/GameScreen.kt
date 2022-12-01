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
import com.dtu.wheeloffortune.data.WheelValuesLocalDataSource
import com.dtu.wheeloffortune.data.WheelValuesRepository
import com.dtu.wheeloffortune.data.WordsLocalDataSource
import com.dtu.wheeloffortune.data.WordsRepository
import com.dtu.wheeloffortune.ui.theme.WheelOfFortuneTheme

/**
 * Shows the game screen
 * @param gameScreenViewModel the view model used to keep track of the game state
 * @param finishApp what should happen if the user pressed the end game button
 */
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    gameScreenViewModel: GameScreenViewModel,
    finishApp: () -> Unit
) {
    // Immutable state of the game
    val gameState = gameScreenViewModel.uiState

    // Check if the game is won of lost on each recompose
    if (gameState.gameStatus == GameCycle.WON || gameState.gameStatus == GameCycle.LOST)
        GameEndedDialog(gameState = gameState, finishApp = { finishApp() }) {
            gameScreenViewModel.resetGame()
        }

    //  Layout of the screen
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

/**
 * Shows a dialog when the game has ended
 * @param gameState the current state of the app
 * @param finishApp action to happen on end game
 * @param playAgain action to happen of play again
 */
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

/**
 * Show a line on top of the screen with the lives and score of the user
 * @param lives the lives the user has left
 * @param score the current score of the user
 */
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

/**
 * Show the amount of lives left for the user
 * @param lives the lives the user has left
 */
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

/**
 * Show the current user score
 * @param score the current score of the user
 */
@Composable
fun UserScore(modifier: Modifier = Modifier, score: Int) =
    Text(text = stringResource(id = R.string.score) + ": $score")

/**
 * Show the word on the screen as buttons. The word is split on underscores which is used to
 * represent spaces and to show it nicely on the screen each spaces is treated as a newline.
 * @param guessedWord the current status of the word the user is guessing on
 */
@Composable
fun Word(
    modifier: Modifier = Modifier,
    guessedWord: String,
) {
    val lines = guessedWord.split("_")

    // Make a row each time a space is encounter
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

/**
 * Shows the keyboard on the screen.
 * @param keys the current state of the keyboard
 * @param enabled if the key is pressable or not
 * @param onGuess what should happen when the key is pressed
 */
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

/**
 * Show a small button that fits on letter on the screen with default spacing in between.
 * @param c the char to show on the button
 * @param enabled if the button is pressable
 * @param onGuess what should happen on key-press
 */
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
    Surface {
        WheelOfFortuneTheme {
            GameScreen(
                gameScreenViewModel = GameScreenViewModel(
                    WordsRepository(
                        WordsLocalDataSource()
                    ),
                    wheelValuesRepository = WheelValuesRepository(WheelValuesLocalDataSource())
                )
            ) {}
        }
    }
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun GameScreenPreviewDarkMode() {
    WheelOfFortuneTheme {
        Surface {
            GameScreen(
                gameScreenViewModel = GameScreenViewModel(
                    WordsRepository(
                        WordsLocalDataSource()
                    ),
                    wheelValuesRepository = WheelValuesRepository(WheelValuesLocalDataSource())
                )
            ) {}
        }
    }
}
