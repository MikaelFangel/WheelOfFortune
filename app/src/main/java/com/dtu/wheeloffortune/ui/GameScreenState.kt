package com.dtu.wheeloffortune.ui

data class GameScreenState(
    val remainingLives: Int = 5,

    val currentCategory: String = "",
    val currentWord: String = "",
    val guessedWord: String = "HELLO WORLD",

    val userScore: Int = 0,
    val isKeyGuessed: HashMap<Char, Boolean> = HashMap()
)
