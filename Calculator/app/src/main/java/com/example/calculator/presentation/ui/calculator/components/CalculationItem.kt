package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.Calculation

@Composable
fun CalculationItem(calculation: Calculation) {

    Text(text = calculation.toString(), modifier = Modifier.padding(vertical = 4.dp))
}