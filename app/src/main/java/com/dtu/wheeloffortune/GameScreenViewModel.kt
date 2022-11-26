package com.dtu.wheeloffortune

import android.util.Log
import androidx.lifecycle.ViewModel
import com.dtu.wheeloffortune.ui.GameScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

            _uiState.update { wordState -> wordState.copy(guessedWord = tempString.toString()) }
        } else {
            _uiState.update { state -> state.copy(remainingLives = uiState.value.remainingLives - 1) }
            checkIfGameLost()
        }
    }

    private fun checkIfGameLost(): Boolean {
        return uiState.value.remainingLives <= 0
    }

    private fun getIndexesOfLetters(c: Char): List<Int> {
        return uiState.value.currentWord
            .lowercase()
            .withIndex()
            .filter { it.value == c }
            .map { it.index }
    }

    private fun resetGame() {
        val randomWordTemp = getRandomWord()
        _uiState.value = GameScreenState(
            currentCategory = getRandomCategory(),
            currentWord = randomWordTemp,
            guessedWord = getCurrentWordAsBlanks(randomWordTemp),
            remainingLives = 5
        )
        initializeKeys()
    }
}