package io.github.sergeyboboshko.cereport.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import kotlinx.coroutines.delay

@Composable
fun WelcomeScreen() {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(500)  // Додаємо затримку для ефекту появи
        visible = true
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(visible = visible, enter = fadeIn()) {
            Text(
                text = "COMPOSE ENTITY",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = GlobalColors.currentPalette.text
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        AnimatedVisibility(visible = visible, enter = fadeIn()) {
            Text(
                text = "Build powerful apps effortlessly!",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                color = GlobalColors.currentPalette.secondary
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        AnimatedVisibility(visible = visible, enter = fadeIn()) {
            BasicText(
                text = "Say goodbye to boilerplate code and hello to rapid development! " +
                        "Compose Entity lets you create database-driven apps with an intuitive UI " +
                        "in just a few lines of code. Simple, powerful, and elegant!",
                style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, color = GlobalColors.currentPalette.text)
            )
        }
    }
}
