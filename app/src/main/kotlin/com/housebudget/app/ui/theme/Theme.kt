package com.housebudget.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.Composable

private val LightScheme = lightColorScheme(
    primary = BrightBlue,
    onPrimary = Color.White,
    secondary = SoftBlue,
    background = SoftBlueVariant,
    onBackground = SlateText,
    surface = Color.White,
    onSurface = SlateText,
)

@Composable
fun HouseBudgetTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightScheme,
        typography = AppTypography,
        content = content
    )
}
