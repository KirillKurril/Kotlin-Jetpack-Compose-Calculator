package com.example.calculator.domain.model

sealed class CalculatorOperation {
    
    object Add : CalculatorOperation()
    object Subtract : CalculatorOperation()
    object Multiply : CalculatorOperation()
    object Divide : CalculatorOperation()

    object Sin : CalculatorOperation()
    object Cos : CalculatorOperation()
    object Tan : CalculatorOperation()
    object Cot : CalculatorOperation()
    object Sqrt : CalculatorOperation()
    object Parentheses : CalculatorOperation()
}