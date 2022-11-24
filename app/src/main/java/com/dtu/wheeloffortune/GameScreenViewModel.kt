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
            _uiState.value.isKeyGuessed[c] = false
    }

    private fun getRandomCategory(): String {
        return ""
    }

    private fun getRandomWord(): String {
        return ""
    }

    private fun getCurrentWordAsBlanks(word: String): String {
        return word.replace("[a-zA-Z]".toRegex(), " ")
    }

    private fun resetGame() {
        initializeKeys()
        _uiState.value = GameScreenState(
            currentCategory = getRandomCategory(),
            currentWord = getRandomWord(),
            guessedWord = getCurrentWordAsBlanks(_uiState.value.currentWord),
            remainingLives = 5
        )
    }
}