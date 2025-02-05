package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.Calculation

@Composable
fun CalculationItem(
    calculation: Calculation,
    onCalculationSelect: (Calculation) -> Unit
) {
    Text(
        text = "${calculation.expression} = ",
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onCalculationSelect(calculation) }
    )
    Text(
        text = calculation.result,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clickable { onCalculationSelect(calculation) }
    )
}