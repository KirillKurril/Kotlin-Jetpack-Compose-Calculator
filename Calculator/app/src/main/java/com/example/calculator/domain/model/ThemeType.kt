package com.example.calculator.domain.model.colorScheme

import androidx.compose.material3.ColorScheme

enum class ThemeType(val colorScheme: ColorScheme) {
    LIGHT(LightColorScheme),
    DARK(DarkColorScheme),
    BRIGHT(BrightColorScheme)
}