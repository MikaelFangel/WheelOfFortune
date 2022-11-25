package com.dtu.wheeloffortune

import androidx.lifecycle.ViewModel
import com.dtu.wheeloffortune.ui.GameScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class GameScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameScreenState())
    val uiState: StateFlow<GameScreenState> = _uiState.asStateFlow()

    init {
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