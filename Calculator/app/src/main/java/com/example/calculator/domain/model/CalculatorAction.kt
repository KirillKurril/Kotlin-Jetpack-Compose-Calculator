package com.example.calculator.domain.model

sealed class CalculatorAction {
    data class Number(val number: String) : CalculatorAction()
    data class Operation(val operation: CalculatorOperation) : CalculatorAction()
    object Calculate : CalculatorAction()
    object Clear : CalculatorAction()
    object Backspace : CalculatorAction()
    object ToggleSign : CalculatorAction()
    object Decimal : CalculatorAction()
    object Percent : CalculatorAction()
}
