package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.CalculatorOperation

@Composable
fun CalculatorButtonsGrid(
    onNumberClick: (String) -> Unit,
    onOperationClick: (CalculatorOperation) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 7..9) {
                Button(
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = i.toString())
                }
            }
            Button(
                onClick = { onOperationClick(CalculatorOperation.Divide) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "÷")
            }
        }
        // Добавьте остальные ряды кнопок аналогично
    }
}