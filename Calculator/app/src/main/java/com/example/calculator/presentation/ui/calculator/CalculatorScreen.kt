package com.example.calculator.presentation.ui.calculator

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculator.domain.model.CalculatorAction
import com.example.calculator.presentation.viewmodel.CalculatorViewModel
import com.example.calculator.presentation.ui.calculator.components.CalculatorDisplay
import com.example.calculator.presentation.ui.calculator.components.CalculatorButtonsGrid

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val state by viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        CalculatorDisplay(
            state = state,
            modifier = Modifier.fillMaxWidth()
        )

        CalculatorButtonsGrid(
            onNumberClick = { number ->
                viewModel.onAction(CalculatorAction.Number(number))
            },
            onOperationClick = { operation ->
                viewModel.onAction(CalculatorAction.Operation(operation))
            },
            onEqualsClick = {
                viewModel.onAction(CalculatorAction.Calculate)
            },
            onClearClick = {
                viewModel.onAction(CalculatorAction.Clear)
            },
            onBackspaceClick = {
                viewModel.onAction(CalculatorAction.Backspace)
            },
            onToggleSignClick = {
                viewModel.onAction(CalculatorAction.ToggleSign)
            },
            onDecimalClick = {
                viewModel.onAction(CalculatorAction.Decimal)
            },
            onPercentClick = {
                viewModel.onAction(CalculatorAction.Percent)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
