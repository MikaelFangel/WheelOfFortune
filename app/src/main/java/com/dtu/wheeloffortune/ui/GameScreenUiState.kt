package com.dtu.wheeloffortune.ui

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

enum class GameCycle {
    SPINNING,
    GUESSING,
    WON,
    LOST
}

data class GameScreenUiState(
    val remainingLives: Int = 5,
    val gameStatus: GameCycle = GameCycle.GUESSING,

    val currentCategory: String = "",
    val currentWord: String = "",
    val guessedWord: String = "",

    val userScore: Int = 0,
    val wheelScore: Int = 0,
    val isKeyGuessed: SnapshotStateMap<Char, Boolean> = mutableStateMapOf()
)
