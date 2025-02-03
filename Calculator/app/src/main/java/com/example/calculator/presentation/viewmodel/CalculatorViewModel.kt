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
            is CalculatorAction.Clear -> {
                _state.value = CalculatorState()
            }
            is CalculatorAction.ClearAll -> {
                _state.value = CalculatorState()
            }
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


        if (currentNum == "0" || (currentNum.isEmpty() && number == "0")) {
            if (number == "0") return
            _state.value = currentState.copy(
                currentNumber = number,
                expression = if (currentState.expression.endsWith("0")) 
                    currentState.expression.dropLast(1) + number
                else 
                    currentState.expression + number
            )
            return
        }

        _state.value = currentState.copy(
            currentNumber = currentNum + number,
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

            CalculatorOperation.Sin -> "sin("
            CalculatorOperation.Cos -> "cos("
            CalculatorOperation.Tan -> "tan("
            CalculatorOperation.Cot -> "cot("
            CalculatorOperation.Sqrt -> "sqrt("
            CalculatorOperation.Parentheses -> {
                val openBrackets = currentState.expression.count { it == '(' }
                val closeBrackets = currentState.expression.count { it == ')' }
                if (openBrackets > closeBrackets) ")" else "("
            }
        }

        if (currentState.expression.isNotEmpty() && 
            RPNCalculator.isOperator(currentState.expression.last()) &&
            operation !in listOf(CalculatorOperation.Sin, CalculatorOperation.Cos, 
                               CalculatorOperation.Tan, CalculatorOperation.Cot, 
                               CalculatorOperation.Sqrt, CalculatorOperation.Parentheses)) {
            return
        }

        if (operation == CalculatorOperation.Parentheses && opSymbol == ")") {
            if (currentState.currentNumber.isEmpty() && 
                currentState.expression.isNotEmpty() && 
                currentState.expression.last() == '(') {
                return
            }
        }

        if (currentState.result.isNotEmpty()) {
            _state.value = CalculatorState(
                expression = when (operation) {
                    CalculatorOperation.Sin, CalculatorOperation.Cos,
                    CalculatorOperation.Tan, CalculatorOperation.Cot,
                    CalculatorOperation.Sqrt -> "$opSymbol${currentState.result})"
                    else -> currentState.result + opSymbol
                },
                currentNumber = ""
            )
            return
        }

        if (currentState.currentNumber.isNotEmpty() || currentState.expression.isNotEmpty() || 
            operation in listOf(CalculatorOperation.Sin, CalculatorOperation.Cos,
                              CalculatorOperation.Tan, CalculatorOperation.Cot,
                              CalculatorOperation.Sqrt)) {
            _state.value = currentState.copy(
                expression = when (operation) {
                    CalculatorOperation.Sin, CalculatorOperation.Cos,
                    CalculatorOperation.Tan, CalculatorOperation.Cot,
                    CalculatorOperation.Sqrt -> {
                        if (currentState.currentNumber.isNotEmpty()) {
                            currentState.expression.dropLast(currentState.currentNumber.length) + 
                            opSymbol + currentState.currentNumber + ")"
                        } else {
                            currentState.expression + opSymbol
                        }
                    }
                    else -> currentState.expression + opSymbol
                },
                currentNumber = ""
            )
        }
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