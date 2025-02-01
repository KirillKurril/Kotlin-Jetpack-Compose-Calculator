package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.domain.model.CalculatorAction
import com.example.calculator.domain.model.CalculatorOperation
import com.example.calculator.domain.state.CalculatorState
import com.example.calculator.domain.util.RPNCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Clear -> clearState()
            is CalculatorAction.Backspace -> backspace()
            is CalculatorAction.ToggleSign -> toggleSign()
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Percent -> enterPercent()
        }
    }

    private fun enterNumber(number: String) {
        val currentState = _state.value
        
        // If we have a result, clear it when entering a new number
        if (currentState.result.isNotEmpty()) {
            clearState()
            enterNumber(number)
            return
        }

        // Check if adding this digit would exceed the 10-digit limit
        val currentNum = currentState.currentNumber
        if (currentNum.replace("-", "").replace(".", "").length >= 10) {
            return
        }

        val newNumber = when {
            currentNum == "0" && number == "0" -> "0"
            currentNum == "0" -> number
            else -> currentNum + number
        }

        _state.value = currentState.copy(
            currentNumber = newNumber,
            expression = currentState.expression + number
        )
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val currentState = _state.value
        val opSymbol = when(operation) {
            CalculatorOperation.Add -> "+"
            CalculatorOperation.Subtract -> "-"
            CalculatorOperation.Multiply -> "*"
            CalculatorOperation.Divide -> "/"
        }

        if (currentState.result.isNotEmpty()) {
            _state.value = CalculatorState(
                expression = currentState.result + opSymbol,
                currentNumber = ""
            )
            return
        }

        if (currentState.currentNumber.isNotEmpty() || currentState.expression.isNotEmpty()) {
            _state.value = currentState.copy(
                expression = currentState.expression + opSymbol,
                currentNumber = ""
            )
        }
    }

    private fun calculate() {
        val currentState = _state.value
        if (currentState.expression.isEmpty()) return

        try {
            val result = RPNCalculator.evaluate(currentState.expression)
            val formattedResult = RPNCalculator.formatResult(result)
            _state.value = currentState.copy(
                result = formattedResult,
                currentNumber = ""
            )
        } catch (e: Exception) {
            _state.value = currentState.copy(isError = true)
        }
    }

    private fun backspace() {
        val currentState = _state.value
        if (currentState.result.isNotEmpty()) {
            clearState()
            return
        }

        if (currentState.expression.isNotEmpty()) {
            val newExpression = currentState.expression.dropLast(1)
            val newCurrentNumber = if (currentState.currentNumber.isNotEmpty()) {
                currentState.currentNumber.dropLast(1)
            } else {
                var num = ""
                var i = newExpression.length - 1
                while (i >= 0 && (newExpression[i].isDigit() || newExpression[i] == '.' || 
                    (newExpression[i] == '-' && (i == 0 || !newExpression[i-1].isDigit())))) {
                    num = newExpression[i] + num
                    i--
                }
                num
            }

            _state.value = currentState.copy(
                expression = newExpression,
                currentNumber = newCurrentNumber
            )
        }
    }

    private fun toggleSign() {
        val currentState = _state.value
        if (currentState.currentNumber.isEmpty()) return

        val newNumber = if (currentState.currentNumber.startsWith("-")) {
            currentState.currentNumber.substring(1)
        } else {
            "-" + currentState.currentNumber
        }

        // Replace the last number in expression with the new signed number
        val expressionWithoutLastNumber = currentState.expression.dropLast(currentState.currentNumber.length)
        _state.value = currentState.copy(
            currentNumber = newNumber,
            expression = expressionWithoutLastNumber + newNumber
        )
    }

    private fun enterDecimal() {
        val currentState = _state.value
        if (currentState.result.isNotEmpty()) {
            clearState()
            enterDecimal()
            return
        }

        if (currentState.currentNumber.contains(".")) return

        val newNumber = when {
            currentState.currentNumber.isEmpty() -> "0."
            else -> currentState.currentNumber + "."
        }

        _state.value = currentState.copy(
            currentNumber = newNumber,
            expression = currentState.expression + if (currentState.currentNumber.isEmpty()) "0." else "."
        )
    }

    private fun enterPercent() {
        val currentState = _state.value
        if (currentState.currentNumber.isEmpty()) return

        _state.value = currentState.copy(
            expression = currentState.expression + "%",
            currentNumber = ""
        )
    }

    private fun clearState() {
        _state.value = CalculatorState()
    }
}