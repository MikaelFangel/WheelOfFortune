package com.dtu.wheeloffortune.data

class WheelValuesLocalDataSource : WheelDataSource {

    override fun getListOfWheelValues(): List<Int> {
        return wheelValues
    }

    private val wheelValues = listOf(
        3000,
        1250,
        800,
        500,
        500,
        500,
        500,
        100,
        800,
        800,
        1000,
        1500,
        600,
        0,
        0
    )
}