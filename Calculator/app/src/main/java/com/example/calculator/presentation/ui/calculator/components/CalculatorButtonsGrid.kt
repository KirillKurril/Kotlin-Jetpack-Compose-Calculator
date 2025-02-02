package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
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
    onBackspaceClick: () -> Unit,
    onToggleSignClick: () -> Unit,
    onDecimalClick: () -> Unit,
    onPercentClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(4.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Top row: Trigonometric functions and sqrt
        Row(
            modifier = Modifier.fillMaxWidth().height(48.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "sin",
                onClick = { onOperationClick(CalculatorOperation.Sin) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 16
            )
            CalculatorButton(
                text = "cos",
                onClick = { onOperationClick(CalculatorOperation.Cos) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 16
            )
            CalculatorButton(
                text = "tan",
                onClick = { onOperationClick(CalculatorOperation.Tan) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 16
            )
            CalculatorButton(
                text = "cot",
                onClick = { onOperationClick(CalculatorOperation.Cot) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 16
            )
            CalculatorButton(
                text = "√",
                onClick = { onOperationClick(CalculatorOperation.Sqrt) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.tertiaryContainer,
                fontSize = 20
            )
        }

        // First row: Clear, Toggle Sign, Percent, Divide
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "C",
                onClick = onClearClick,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.errorContainer
            )
            CalculatorButton(
                text = "±",
                onClick = onToggleSignClick,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            CalculatorButton(
                text = "%",
                onClick = onPercentClick,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            CalculatorButton(
                text = "()",
                onClick = { onOperationClick(CalculatorOperation.Parentheses) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        // Second row: 7, 8, 9, Multiply
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 7..9) {
                CalculatorButton(
                    text = i.toString(),
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            CalculatorButton(
                text = "×",
                onClick = { onOperationClick(CalculatorOperation.Multiply) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        // Third row: 4, 5, 6, Subtract
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 4..6) {
                CalculatorButton(
                    text = i.toString(),
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            CalculatorButton(
                text = "-",
                onClick = { onOperationClick(CalculatorOperation.Subtract) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        // Fourth row: 1, 2, 3, Add
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (i in 1..3) {
                CalculatorButton(
                    text = i.toString(),
                    onClick = { onNumberClick(i.toString()) },
                    modifier = Modifier.weight(1f),
                    color = MaterialTheme.colorScheme.surfaceVariant
                )
            }
            CalculatorButton(
                text = "+",
                onClick = { onOperationClick(CalculatorOperation.Add) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        // Fifth row: 0, Decimal, Backspace, Divide
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "0",
                onClick = { onNumberClick("0") },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CalculatorButton(
                text = ".",
                onClick = onDecimalClick,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            CalculatorButton(
                text = "⌫",
                onClick = onBackspaceClick,
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.errorContainer
            )
            CalculatorButton(
                text = "÷",
                onClick = { onOperationClick(CalculatorOperation.Divide) },
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primaryContainer
            )
        }

        // Last row: Equals
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CalculatorButton(
                text = "=",
                onClick = onEqualsClick,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}