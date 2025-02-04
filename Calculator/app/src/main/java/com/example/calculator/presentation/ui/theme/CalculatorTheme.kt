package com.example.calculator.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.calculator.domain.model.colorScheme.ThemeType
import com.example.calculator.domain.usecase.GetThemeUseCase
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.LaunchedEffect


@Composable
fun CalculatorTheme(
    getThemeUseCase: GetThemeUseCase,
    content: @Composable () -> Unit
) {
    val colorScheme = remember { mutableStateOf(ThemeType.LIGHT.colorScheme) }

    LaunchedEffect(Unit) {
        colorScheme.value = getThemeUseCase().colorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme.value,
        typography = Typography,
        content = content
    )
}