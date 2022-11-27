package com.dtu.wheeloffortune.ui

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap

data class GameScreenState(
    val remainingLives: Int = 5,
    val gameEnded: Boolean = false,

    val currentCategory: String = "",
    val currentWord: String = "",
    val guessedWord: String = "",

    val userScore: Int = 0,
    val wheelScore: Int = 0,
    val isKeyGuessed: SnapshotStateMap<Char, Boolean> = mutableStateMapOf()
)
