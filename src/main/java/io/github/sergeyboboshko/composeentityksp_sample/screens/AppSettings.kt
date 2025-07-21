package io.github.sergeyboboshko.composeentityksp_sample.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.sergeyboboshko.composeentity.daemons.ColorPalette
import io.github.sergeyboboshko.composeentity.daemons.GlobalColors
import io.github.sergeyboboshko.composeentity.daemons.StyledButton
import kotlin.math.roundToInt

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AppSettings() {
    // Варіанти, які кольори можна змінювати
    val options = listOf(
        "text", "primary", "secondary", "background", "button",
        "buttonText", "selectedBackground"
    )

    var selectedKey by remember { mutableStateOf("background") }

    // Поточна палітра
    val currentPalette = GlobalColors.currentPalette

    // Дістаємо поточний колір залежно від вибору
    val currentColor = remember(selectedKey) {
        when (selectedKey) {
            "text" -> currentPalette.text
            "primary" -> currentPalette.primary
            "secondary" -> currentPalette.secondary
            "background" -> currentPalette.background
            "button" -> currentPalette.button
            "buttonText" -> currentPalette.buttonText
            "selectedBackground" -> currentPalette.selectedBackground
            else -> Color.Gray
        }
    }

    // Стани повзунків
    var red by remember { mutableStateOf(currentColor.red * 255f) }
    var green by remember { mutableStateOf(currentColor.green * 255f) }
    var blue by remember { mutableStateOf(currentColor.blue * 255f) }

    // Новий колір
    val newColor = Color(red / 255f, green / 255f, blue / 255f)

    LazyColumn(modifier = Modifier.padding(16.dp)) {
        item{
            Text("🎨 Що редагуємо:")
        }
        item {
            FlowRow {
                // Вибір елементу палітри

                options.forEach { key ->
                    StyledButton(
                        onClick = {
                            selectedKey = key
                            val col = when (key) {
                                "text" -> currentPalette.text
                                "primary" -> currentPalette.primary
                                "secondary" -> currentPalette.secondary
                                "background" -> currentPalette.background
                                "button" -> currentPalette.button
                                "buttonText" -> currentPalette.buttonText
                                "selectedBackground" -> currentPalette.selectedBackground
                                else -> Color.Gray
                            }
                            red = col.red * 255f
                            green = col.green * 255f
                            blue = col.blue * 255f
                        },
                        modifier = Modifier.weight(1f),
                        backgroundColor = if (selectedKey == key)
                            Color.Yellow
                        else
                            GlobalColors.currentPalette.button // <--- ✅ тут динамічний фон
                    ) {
                        Text(
                            text = key,
                            color = GlobalColors.currentPalette.buttonText // 🔸 ще й текст можна зробити залежним
                        )
                    }
                }
            }

        }

        item {
            Text("🔴 Red: ${red.roundToInt()}")
            Slider(value = red, onValueChange = { red = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text("🟢 Green: ${green.roundToInt()}")
            Slider(value = green, onValueChange = { green = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                },
                modifier = Modifier.fillMaxWidth()
            )
            Text("🔵 Blue: ${blue.roundToInt()}")
            Slider(value = blue, onValueChange = { blue = it }, valueRange = 0f..255f,
                onValueChangeFinished = {
                    applyNewColorToPalette(selectedKey, newColor)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            FlowRow {
                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.background)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Text",
                        modifier = Modifier.background(GlobalColors.currentPalette.background),
                        color = GlobalColors.currentPalette.text
                    )
                }

                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.background)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Background",
                        modifier = Modifier.background(GlobalColors.currentPalette.background),
                        color = GlobalColors.currentPalette.text
                    )
                }

                Card(
                    Modifier
                        .background(GlobalColors.currentPalette.selectedBackground)
                        .padding(4.dp)
                ) {
                    Text(
                        text = "Preview Selected",
                        modifier = Modifier.background(GlobalColors.currentPalette.selectedBackground),
                        color = GlobalColors.currentPalette.text
                    )
                }
            }
        }
    }
}

fun applyNewColorToPalette(key: String, color: Color) {
    val p = GlobalColors.currentPalette
    val newPalette = ColorPalette(
        primary = if (key == "primary") color else p.primary,
        secondary = if (key == "secondary") color else p.secondary,
        background = if (key == "background") color else p.background,
        selectedBackground = if (key == "selectedBackground") color else p.selectedBackground,
        text = if (key == "text") color else p.text,
        button = if (key == "button") color else p.button,
        buttonText = if (key == "buttonText") color else p.buttonText,
        captionFonColor = p.captionFonColor,
        editBackgroundColor = p.editBackgroundColor,
        topAreaTextColor = p.topAreaTextColor,
        topAreaBackgroundColor = p.topAreaBackgroundColor,
        bottomAreaTextColor = p.bottomAreaTextColor,
        bottomAreaBackgroundColor = p.bottomAreaBackgroundColor
    )

    GlobalColors.customizePalette(newPalette)
}
