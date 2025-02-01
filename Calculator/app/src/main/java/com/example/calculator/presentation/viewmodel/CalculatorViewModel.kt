package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.domain.model.CalculatorAction
import com.example.calculator.domain.model.CalculatorOperation
import com.example.calculator.domain.state.CalculatorState
import com.example.calculator.domain.util.RPNCalculator
import com.example.calculator.domain.util.DivisionByZeroException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor() : ViewModel() {
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state
    private var errorMessageJob: Job? = null

    fun onAction(action: CalculatorAction) {
        errorMessageJob?.cancel()
        _state.value = _state.value.copy(errorMessage = null)

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
        
        if (currentState.result.isNotEmpty()) {
            clearState()
            enterNumber(number)
            return
        }

        val currentNum = currentState.currentNumber
        if (currentNum.replace("-", "").replace(".", "").length >= 15) {
            showTemporaryMessage("Максимальная длина числа - 15 символов")
            return
        }

        val newNumber = when {
            currentNum == "0" -> number // Если текущее число 0, заменяем его на вводимую цифру
            currentNum.isEmpty() && number == "0" -> "0" // Если ввод начинается с 0
            else -> currentNum + number
        }

        val newExpression = if (currentNum == "0" && number != "0") {
            // Если текущее число 0 и вводим не 0, заменяем последний символ в выражении
            currentState.expression.dropLast(1) + number
        } else {
            currentState.expression + number
        }

        _state.value = currentState.copy(
            currentNumber = newNumber,
            expression = newExpression
        )
    }

    private fun showTemporaryMessage(message: String) {
        errorMessageJob?.cancel()
        errorMessageJob = viewModelScope.launch {
            _state.value = _state.value.copy(errorMessage = message)
            delay(3000)
            if (isActive) {
                _state.value = _state.value.copy(errorMessage = null)
            }
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val currentState = _state.value
        val opSymbol = when(operation) {
            CalculatorOperation.Add -> "+"
            CalculatorOperation.Subtract -> "-"
            CalculatorOperation.Multiply -> "*"
            CalculatorOperation.Divide -> "/"
        }

        if (currentState.expression.isNotEmpty() && 
            RPNCalculator.isOperator(currentState.expression.last())) {
            return
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
                currentNumber = "",
                isError = false,
                errorMessage = null
            )
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is DivisionByZeroException -> "Деление на ноль невозможно"
                else -> "Ошибка в выражении"
            }
            _state.value = currentState.copy(
                isError = true,
                errorMessage = errorMessage
            )
            showTemporaryMessage(errorMessage)
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

        val isFirstNumber = currentState.expression.takeWhile { it.isDigit() || it == '-' || it == '.' }
            .let { it.isEmpty() || it == currentState.currentNumber }

        val expressionWithoutLastNumber = currentState.expression.dropLast(currentState.currentNumber.length)
        val newNumberWithBrackets = if (!isFirstNumber && newNumber.startsWith("-")) {
            "($newNumber)"
        } else {
            newNumber
        }

        _state.value = currentState.copy(
            currentNumber = newNumber,
            expression = expressionWithoutLastNumber + newNumberWithBrackets
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
        if (currentState.currentNumber.isEmpty() || 
            (currentState.expression.isNotEmpty() && 
             RPNCalculator.isOperator(currentState.expression.last()))) return

        _state.value = currentState.copy(
            expression = currentState.expression + "%",
            currentNumber = ""
        )
    }

    private fun clearState() {
        errorMessageJob?.cancel()
        _state.value = CalculatorState()
    }
}