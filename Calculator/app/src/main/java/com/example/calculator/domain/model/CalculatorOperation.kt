package com.example.calculator.domain.model

sealed class CalculatorOperation {
    object Add : CalculatorOperation()
    object Subtract : CalculatorOperation()
    object Multiply : CalculatorOperation()
    object Divide : CalculatorOperation()
}