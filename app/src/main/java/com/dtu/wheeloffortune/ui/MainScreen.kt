package com.dtu.wheeloffortune.ui

import androidx.compose.material.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun MainScreen(
    startGame: () -> Unit
) {
    Button(onClick = startGame) {
        Text(text = "Go To Game")
    }
}