package com.dtu.wheeloffortune.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.dtu.wheeloffortune.ui.theme.WheelOfFortuneTheme

@Composable
fun GameEndedScreen() {
    Text("GOTCHA")
}

@Preview(showBackground = true)
@Composable
fun GameEndedScreenPreview() {
    WheelOfFortuneTheme {
        GameEndedScreen()
    }
}