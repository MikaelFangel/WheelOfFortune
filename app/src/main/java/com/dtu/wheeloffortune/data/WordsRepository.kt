package com.dtu.wheeloffortune.data

import kotlin.random.Random

class WordsRepository(
    private val wordsLocalDataSource: WordsDataSource
) {
    fun getRandomCategory(): String {
        val cats = wordsLocalDataSource.getListOfCategories()
        return cats.random(Random(System.currentTimeMillis()))
    }

    fun getRandomWordFromCategory(category: String): String {
        val words = wordsLocalDataSource.getListOfWordsInCategory(category)
        return words?.random(Random(System.currentTimeMillis())).toString()
    }
}