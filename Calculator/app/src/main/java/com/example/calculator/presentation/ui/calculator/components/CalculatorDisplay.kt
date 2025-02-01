package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.domain.state.CalculatorState

@Composable
fun CalculatorDisplay(
    state: CalculatorState,
    modifier: Modifier = Modifier
) {
    Text(
        text = when {
            state.result.isNotEmpty() -> state.result
            state.number2.isNotEmpty() -> state.number2
            state.number1.isNotEmpty() -> state.number1
            else -> "0"
        },
        textAlign = TextAlign.End,
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        fontSize = 48.sp
    )
}