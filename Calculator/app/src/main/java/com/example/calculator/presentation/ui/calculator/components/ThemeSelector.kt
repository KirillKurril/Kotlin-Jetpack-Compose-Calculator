package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.colorScheme.ThemeType

@Composable
fun ThemeSelector(
    currentTheme: ThemeType,
    onThemeSelected: (ThemeType) -> Unit
) {
    Row(modifier = Modifier.padding(16.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
        ThemeType.entries.forEach { themeType ->
            val isSelected = themeType == currentTheme
            Surface(
                modifier = Modifier
                    .size(50.dp)
                    .padding(8.dp)
                    .clickable { onThemeSelected(themeType) }
                    .border(
                        width = if (isSelected) 2.dp else 0.dp,
                        color = if (isSelected) Color.Yellow else Color.Transparent,
                        shape = CircleShape
                    ),
                shape = CircleShape,
                color = themeType.colorScheme.onPrimary
            ) {}
        }
    }
}