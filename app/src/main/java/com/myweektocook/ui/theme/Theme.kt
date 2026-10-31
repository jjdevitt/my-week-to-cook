package com.myweektocook.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val WarmTerracotta = Color(0xFFB84A27)
private val DeepHerbGreen = Color(0xFF2E5A44)
private val WarmCream = Color(0xFFFAF7F2)
private val CardSurface = Color(0xFFFFFFFF)
private val SoftSand = Color(0xFFF0EAE1)
private val AmberWarning = Color(0xFFD97706)

private val LightColors = lightColorScheme(
    primary = WarmTerracotta,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFDBD0),
    onPrimaryContainer = Color(0xFF3A0B00),
    secondary = DeepHerbGreen,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD2E8DA),
    onSecondaryContainer = Color(0xFF092114),
    tertiary = AmberWarning,
    background = WarmCream,
    surface = CardSurface,
    surfaceVariant = SoftSand
)

@Composable
fun MyWeekToCookTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
