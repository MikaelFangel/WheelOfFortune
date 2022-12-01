package com.dtu.wheeloffortune.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.dtu.wheeloffortune.data.WheelValuesRepository
import com.dtu.wheeloffortune.data.WordsRepository
import java.util.*

class GameScreenViewModel(
    private val wordsRepository: WordsRepository,
    private val wheelValuesRepository: WheelValuesRepository
) : ViewModel() {
    var uiState by mutableStateOf(GameScreenUiState())
        private set

    init {
        resetGame()
    }

    /**
     * Initialize the keyboard used in the game.
     */
    private fun initializeKeys() {
        for (c in 'a'..'z')
            uiState.isKeyGuessed[c] = true
    }

    /**
     * Convert the word to spaces and replace spaces with underscores
     * @param word the word that should be converted
     */
    private fun getCurrentWordAsBlanks(word: String): String {
        return word
            .replace(" ", "_")
            .replace("[a-zA-Z]".toRegex(), " ")
    }

    /**
     * Handle what happens when the user presses a key on the screen and update the state
     * accordingly
     * @param c the key pressed by the player
     */
    fun keyPress(c: Char) {
        uiState.isKeyGuessed[c] = false

        val indexes = getIndexesOfLetters(c)
        // If the letter is found show it on the screen and check if the player has won
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
        } else { // The letters is not in the word and the player should loose one life
            uiState = uiState.copy(
                remainingLives = uiState.remainingLives - 1,
                gameStatus = if (checkIfGameLost())
                    GameCycle.LOST
                else
                    GameCycle.SPINNING
            )
        }
    }

    /**
     * Check if player has run out of lives and thereby lost.
     * @return true is the game is lost
     */
    private fun checkIfGameLost(): Boolean {
        return uiState.remainingLives <= 1
    }

    /**
     * Checks if the game has been won
     * @param guessedWord the user guess on what the word is.
     * @return true if the game is won
     */
    private fun checkIfGameWon(guessedWord: String): Boolean {
        return uiState.currentWord
            .lowercase(Locale.getDefault())
            .replace(" ", "_") == guessedWord.lowercase(Locale.getDefault())
    }

    /**
     * Search for all indexes of a specific letter in the current game word and return a list
     * with the indexes.
     * @param c the letter to search for
     * @return a list of the indexes of the searched letter. The list is empty if the letter
     * is not found
     */
    private fun getIndexesOfLetters(c: Char): List<Int> {
        return uiState.currentWord
            .lowercase()
            .withIndex()
            .filter { it.value == c }
            .map { it.index }
    }

    /**
     * Get the value for what a letters is worth and updates the game state by change the game cycle
     * and updating the user score passed on the value. if the result is 0 the user score is reset
     * as it is the pseudo value for bankrupt.
     */
    fun spinWheel() {
        val letterValue = wheelValuesRepository.getRandomWheelValue()
        uiState = uiState.copy(
            wheelScore = letterValue,
            gameStatus = if (letterValue == 0) GameCycle.SPINNING else GameCycle.GUESSING,
            userScore = if (letterValue == 0) 0 else uiState.userScore
        )
    }

    /**
     * Resets the game and all the game state
     */
    fun resetGame() {
        val catTemp = wordsRepository.getRandomCategory()
        val randomWordTemp = wordsRepository.getRandomWordFromCategory(catTemp)
        uiState = uiState.copy(
            currentCategory = catTemp,
            currentWord = randomWordTemp,
            guessedWord = getCurrentWordAsBlanks(randomWordTemp),
            gameStatus = GameCycle.GUESSING,
            remainingLives = 5,
            userScore = 0
        )
        spinWheel()
        initializeKeys()
    }
}