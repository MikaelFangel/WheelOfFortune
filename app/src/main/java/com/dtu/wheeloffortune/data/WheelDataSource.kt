package com.dtu.wheeloffortune.data

interface WheelDataSource {
    fun getListOfWheelValues(): List<Int>
}