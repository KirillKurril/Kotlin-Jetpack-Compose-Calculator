package com.example.calculator.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.calculator.domain.model.colorScheme.ThemeType

@Composable
fun CalculatorTheme(
    theme: ThemeType,
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = theme.colorScheme,
        typography = Typography,
        content = content
    )
}