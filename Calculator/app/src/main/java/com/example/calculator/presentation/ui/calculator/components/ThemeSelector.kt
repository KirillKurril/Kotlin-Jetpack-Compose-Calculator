package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.colorScheme.ThemeType

@Composable
fun ThemeSelector(
    currentTheme: ThemeType,
    onThemeSelected: (ThemeType) -> Unit
) {
    Box(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center) // Центрируем весь блок
    ) {
        Surface(
            modifier = Modifier
                .padding(32.dp)
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Выберите тему", style = MaterialTheme.typography.titleLarge)

                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ThemeType.entries.forEach { themeType ->
                        val isSelected = themeType == currentTheme
                        Surface(
                            modifier = Modifier
                                .size(50.dp)
                                .padding(8.dp)
                                .clickable { onThemeSelected(themeType) },
                            shape = CircleShape,
                            color = themeType.colorScheme.primaryContainer,
                            border = BorderStroke(
                                width = if (isSelected) 2.dp else 0.dp,
                                color = if (isSelected) Color.Yellow else Color.Transparent
                            )
                        ) {}
                    }
                }
            }
        }
    }
}
