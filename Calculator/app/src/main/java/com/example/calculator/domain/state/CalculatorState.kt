package com.example.calculator.domain.state

import com.example.calculator.domain.model.Calculation

data class CalculatorState(
    val expression: String = "",
    val result: String = "",
    val currentNumber: String = "",
    val isError: Boolean = false,
    val errorMessage: String? = null
)
{
    fun toCalculation(): Calculation {
        return Calculation(
            id = "",
            expression = this.expression,
            result = this.result,
            currentNumber = this.currentNumber
        )
    }
}