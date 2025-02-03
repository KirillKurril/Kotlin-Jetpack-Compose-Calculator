// D:/uni/PRMP/Calculator/app/src/main/java/com/example/calculator/presentation/ui/CalculatorScreen.kt
package com.example.calculator.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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

    Column(modifier = Modifier.fillMaxSize()) {
        CalculatorDisplay(
            state = state,
            onAction = viewModel::onAction,
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
            modifier = Modifier.weight(1f)
        )
    }
}