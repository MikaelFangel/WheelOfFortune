package com.dtu.wheeloffortune.data

interface WordsDataSource {
    fun getListOfCategories(): List<String>
    fun getListOfWordsInCategory(category: String): List<String>?
}