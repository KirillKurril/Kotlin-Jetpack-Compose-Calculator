package com.example.calculator.domain.state

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val currentNumber: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)