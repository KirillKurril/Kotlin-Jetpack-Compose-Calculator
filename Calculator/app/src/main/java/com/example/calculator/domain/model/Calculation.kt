package com.example.calculator.domain.model

import com.example.calculator.domain.state.CalculatorState

data class Calculation(
    val expression: String = "",
    val result: String = "",
    var timestamp: Long = 0L
)
{
    fun toState() : CalculatorState
    {
        return CalculatorState(
            expression = this.expression,
            result = this.result,
            currentNumber = "",
            isError = false,
            errorMessage = null
        )
    }
}