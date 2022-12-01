package com.dtu.wheeloffortune.data

class WordsLocalDataSource(): WordsDataSource {
    override fun getListOfCategories(): List<String> {
        return categories.keys.toList()
    }

    override fun getListOfWordsInCategory(category: String): List<String>? {
        return categories[category]
    }

    private val titleList = listOf(
        "Frost",
        "Aladdin",
        "Top Gun",
        "Avatar",
        "Bohemian Rhapsody",
        "Harry Potter",
        "Dune",
        "The Lord of the Rings"
    )

    private val animalList = listOf(
        "Dog",
        "Rabbit",
        "Cat",
        "Elephant",
        "Seagull",
        "Baboon",
        "Bear",
        "Beaver",
        "Cockroach",
        "Jackal",
        "Languor",
        "Sea Otter",
        "Zebra"
    )

    private val placeList = listOf(
        "Bruxelles",
        "Amsterdam",
        "Odense",
        "The Moon",
        "Mordor",
    )

    private val nameList = listOf(
        "Lars Von Trier",
        "Kayne West",
        "Donald Trump",
        "Elon Musk",
        "Mads Mikkelsen",
    )

    private val categories =
        hashMapOf(
            "title" to titleList,
            "animal" to animalList,
            "places" to placeList,
            "name" to nameList
        )
}