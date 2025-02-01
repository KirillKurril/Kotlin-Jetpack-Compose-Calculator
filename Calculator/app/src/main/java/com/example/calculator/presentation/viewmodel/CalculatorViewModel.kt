package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.domain.model.CalculatorAction
import com.example.calculator.domain.model.CalculatorOperation
import com.example.calculator.domain.state.CalculatorState
import com.example.calculator.domain.usecase.CalculateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(
    private val calculateUseCase: CalculateUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state

    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> calculate()
            is CalculatorAction.Clear -> clearState()
        }
    }
    
    private fun enterNumber(number: String) {
        val currentState = _state.value
        
        // If we have a result and user enters a new number, clear the state first
        if (currentState.result.isNotEmpty()) {
            _state.value = CalculatorState()
            enterNumber(number)
            return
        }

        if (currentState.operation == null) {
            // Handle number1
            val newNumber = when {
                currentState.number1 == "0" && number == "0" -> "0"
                currentState.number1 == "0" -> number
                else -> currentState.number1 + number
            }
            _state.value = currentState.copy(number1 = newNumber)
        } else {
            // Handle number2
            val newNumber = when {
                currentState.number2 == "0" && number == "0" -> "0"
                currentState.number2 == "0" -> number
                else -> currentState.number2 + number
            }
            _state.value = currentState.copy(number2 = newNumber)
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        val currentState = _state.value
        if (currentState.number1.isNotEmpty()) {
            // If we have a result, use it as number1 for the next operation
            if (currentState.result.isNotEmpty()) {
                _state.value = CalculatorState(
                    number1 = currentState.result,
                    operation = operation
                )
            } else {
                _state.value = currentState.copy(operation = operation)
            }
        }
    }

    private fun calculate() {
        val currentState = _state.value
        if (currentState.operation != null && currentState.number2.isNotEmpty()) {
            val result = calculateUseCase(
                number1 = currentState.number1,
                number2 = currentState.number2,
                operation = currentState.operation
            )
            _state.value = currentState.copy(
                result = result,
                number1 = result,
                number2 = "",
                operation = null
            )
        }
    }

    private fun clearState() {
        _state.value = CalculatorState()
    }
}