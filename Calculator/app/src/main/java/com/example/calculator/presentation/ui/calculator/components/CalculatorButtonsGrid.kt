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
    onEqualsClick: () -> Unit,
    onClearClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // First row: 7, 8, 9, ÷
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

        // Second row: 4, 5, 6, ×
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 4..6) {
                Button(
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = i.toString())
                }
            }
            Button(
                onClick = { onOperationClick(CalculatorOperation.Multiply) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "×")
            }
        }

        // Third row: 1, 2, 3, -
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 1..3) {
                Button(
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = i.toString())
                }
            }
            Button(
                onClick = { onOperationClick(CalculatorOperation.Subtract) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "-")
            }
        }

        // Fourth row: C, 0, =, +
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { onClearClick() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "C")
            }
            Button(
                onClick = { onNumberClick("0") },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "0")
            }
            Button(
                onClick = { onEqualsClick() },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "=")
            }
            Button(
                onClick = { onOperationClick(CalculatorOperation.Add) },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "+")
            }
        }
    }
}