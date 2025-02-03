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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlin.math.abs


@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {
    val state by viewModel.state
    var lastSwipeTime = 0L
    val minSwipeDistance = 50f
    val minTimeBetweenSwipes = 300L


    Column(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    val currentTime = System.currentTimeMillis()
                    if (currentTime - lastSwipeTime >= minTimeBetweenSwipes && 
                        abs(dragAmount) >= minSwipeDistance) {
                        when {
                            dragAmount > 0 -> viewModel.onAction(CalculatorAction.ClearAll)
                            dragAmount < 0 -> viewModel.onAction(CalculatorAction.Backspace)
                        }
                        lastSwipeTime = currentTime
                    }
                }
            }
    )   {
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
            modifier = Modifier.weight(1f)
        )
    }
}