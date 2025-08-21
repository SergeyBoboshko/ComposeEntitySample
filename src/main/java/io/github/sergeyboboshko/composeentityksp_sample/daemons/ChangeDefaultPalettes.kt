package io.github.sergeyboboshko.composeentityksp_sample.daemons

import androidx.compose.ui.graphics.Color
import io.github.sergeyboboshko.composeentity.daemons.ColorPalette
import io.github.sergeyboboshko.composeentity.daemons.DefaultDarkPalette
import io.github.sergeyboboshko.composeentity.daemons.DefaultLightPalette

fun changeDefaultPalettes(){
    DefaultLightPalette=    ColorPalette(
        primary = Color(0xFF000000),
        secondary = Color(0xFF505050),
        background = Color(0xFFFFFFFF),
        selectedBackground = Color(200,255,200),
        text = Color(0xFF202020),
        button = Color(0xFFC4E59F),
        buttonText = Color(0xFF000000),
        captionFonColor= Color(0xFFFDF4B5),
        editBackgroundColor = Color(0xFFC5C5C5),
        topAreaTextColor = Color(0xFF0C2E33),
        topAreaBackgroundColor = Color(0xFFDCE0BC),
        bottomAreaTextColor = Color(0xFF3B2C01),
        bottomAreaBackgroundColor = Color(0xFFFDF3D5)
    )
    DefaultDarkPalette=    ColorPalette(
        primary = Color(0xFFFFFFFC),
        secondary = Color(0xFFA0A0A0),
        background = Color(0xFF1E1F22),
        selectedBackground = Color(74, 79, 79, 255),
        text = Color(0xFFFFFFFF),
        button = Color(0xFF505256),
        buttonText = Color(0xFFFFFFFF),
        captionFonColor=Color(0xFF2B2D30),
        editBackgroundColor = Color(0xFF404040),
        topAreaTextColor = Color(0xFFB5F1FC),
        topAreaBackgroundColor = Color(0xFF3C3F41),
        bottomAreaTextColor = Color(0xFFFDF3D5),
        bottomAreaBackgroundColor = Color(0xFF2C242D)
    )
}