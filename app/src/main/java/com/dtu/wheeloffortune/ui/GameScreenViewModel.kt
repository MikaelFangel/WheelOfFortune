package com.dtu.wheeloffortune.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.random.Random

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameScreenState())
    val uiState: StateFlow<GameScreenState> = _uiState.asStateFlow()

    init {
        Log.d("Init", "The viewModel have been init")
        resetGame()
    }

    private fun initializeKeys() {
        for (c in 'a'..'z')
            _uiState.value.isKeyGuessed[c] = true
    }

    private fun getRandomCategory(): String {
        return "TestCat"
    }

    private fun getRandomWord(): String {
        return "TestWord"
    }

    private fun getCurrentWordAsBlanks(word: String): String {
        return word.replace("[a-zA-Z]".toRegex(), " ")
    }

    fun keyPress(c: Char) {
        _uiState.value.isKeyGuessed[c] = false

        val indexes = getIndexesOfLetters(c)
        if (indexes.isNotEmpty()) {
            val tempString = StringBuilder(uiState.value.guessedWord)
            for (i in indexes)
                tempString[i] = c

            _uiState.update { wordState ->
                wordState.copy(
                    guessedWord = tempString.toString(),
                    userScore = uiState.value.userScore +
                            uiState.value.wheelScore * indexes.size
                )
            }
        } else {
            _uiState.update { state ->
                state.copy(
                    remainingLives = uiState.value.remainingLives - 1,
                    gameEnded = checkIfGameLost()
                )
            }
        }
    }

    private fun checkIfGameLost(): Boolean {
        return uiState.value.remainingLives <= 1
    }

    private fun getIndexesOfLetters(c: Char): List<Int> {
        return uiState.value.currentWord
            .lowercase()
            .withIndex()
            .filter { it.value == c }
            .map { it.index }
    }

    fun spinWheel(): Int {
        val wheelValues =
            listOf(3000, 1250, 800, 500, 500, 500, 500, 100, 800, 800, 1000, 1500, 600)
        return wheelValues.random(Random(System.currentTimeMillis()))
    }

    private fun resetGame() {
        val randomWordTemp = getRandomWord()
        _uiState.value = GameScreenState(
            currentCategory = getRandomCategory(),
            currentWord = randomWordTemp,
            guessedWord = getCurrentWordAsBlanks(randomWordTemp),
            remainingLives = 5,
            wheelScore = spinWheel()
        )
        initializeKeys()
    }
}