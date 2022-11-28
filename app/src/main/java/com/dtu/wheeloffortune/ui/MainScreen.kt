package com.dtu.wheeloffortune.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.res.ResourcesCompat
import com.dtu.wheeloffortune.R
import com.dtu.wheeloffortune.ui.theme.WheelOfFortuneTheme

@Composable
fun MainScreen(
    startGame: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.welcome_text_wheeloffortune),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(350.dp)
        )
    }
    Row() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Button(
                onClick = startGame,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                elevation = ButtonDefaults.elevatedButtonElevation(4.dp)
            ) {
                Text(text = stringResource(id = R.string.start_game))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    WheelOfFortuneTheme() {
        MainScreen {
            {}
        }
    }
}