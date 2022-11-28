package com.dtu.wheeloffortune.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dtu.wheeloffortune.data.categories
import com.dtu.wheeloffortune.data.wheelValues
import java.util.*
import kotlin.random.Random

class GameScreenViewModel : ViewModel() {
    var uiState by mutableStateOf(GameScreenUiState())
        private set

    init {
        resetGame()
    }

    private fun initializeKeys() {
        for (c in 'a'..'z')
            uiState.isKeyGuessed[c] = true
    }

    private fun getRandomCategory(): String {
        val cats = categories.keys.toList()
        return cats.random(Random(System.currentTimeMillis()))
    }

    private fun getRandomWord(category: String): String {
        val words = categories[category]
        return words?.random(Random(System.currentTimeMillis())).toString()
    }

    private fun getCurrentWordAsBlanks(word: String): String {
        return word
            .replace(" ", "_")
            .replace("[a-zA-Z]".toRegex(), " ")
    }

    fun keyPress(c: Char) {
        uiState.isKeyGuessed[c] = false

        val indexes = getIndexesOfLetters(c)
        if (indexes.isNotEmpty()) {
            val tempString = StringBuilder(uiState.guessedWord)
            for (i in indexes)
                tempString[i] = c

            uiState = uiState.copy(
                guessedWord = tempString.toString(),
                userScore = uiState.userScore +
                        uiState.wheelScore * indexes.size,
                gameStatus = if (checkIfGameWon(tempString.toString()))
                    GameCycle.WON
                else
                    GameCycle.SPINNING
            )
        } else {
            uiState = uiState.copy(
                remainingLives = uiState.remainingLives - 1,
                gameStatus = if (checkIfGameLost())
                    GameCycle.LOST
                else
                    GameCycle.SPINNING
            )
        }
    }

    private fun checkIfGameLost(): Boolean {
        return uiState.remainingLives <= 1
    }

    private fun checkIfGameWon(guessedWord: String): Boolean {
        return uiState.currentWord
            .lowercase(Locale.getDefault())
            .replace(" ", "_") == guessedWord.lowercase(Locale.getDefault())
    }

    private fun getIndexesOfLetters(c: Char): List<Int> {
        return uiState.currentWord
            .lowercase()
            .withIndex()
            .filter { it.value == c }
            .map { it.index }
    }

    fun spinWheel() {
        val letterValue = wheelValues.random(Random(System.currentTimeMillis()))
        uiState = uiState.copy(
            wheelScore = letterValue,
            gameStatus = if (letterValue == 0) GameCycle.SPINNING else GameCycle.GUESSING,
            userScore = if (letterValue == 0) 0 else uiState.userScore
        )
    }

    fun resetGame() {
        val catTemp = getRandomCategory()
        val randomWordTemp = getRandomWord(catTemp)
        uiState = uiState.copy(
            currentCategory = catTemp,
            currentWord = randomWordTemp,
            guessedWord = getCurrentWordAsBlanks(randomWordTemp),
            remainingLives = 5,
        )
        spinWheel()
        initializeKeys()
    }
}