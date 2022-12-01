package com.dtu.wheeloffortune.data

import kotlin.random.Random

class WheelValuesRepository(
    private val wheelValuesLocalDataSource: WheelDataSource
) {
    fun getRandomWheelValue(): Int {
        return wheelValuesLocalDataSource.getListOfWheelValues()
            .random(Random(System.currentTimeMillis()))
    }
}