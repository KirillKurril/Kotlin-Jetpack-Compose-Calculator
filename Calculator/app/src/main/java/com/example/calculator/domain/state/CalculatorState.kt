package com.example.calculator.domain.state

import com.example.calculator.domain.model.CalculatorOperation

data class CalculatorState(
    val number1: String = "",
    val number2: String = "",
    val operation: CalculatorOperation? = null,
    val result: String = ""
)