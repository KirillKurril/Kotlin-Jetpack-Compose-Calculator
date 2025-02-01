package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.CalculatorOperation

class CalculateUseCase {
    operator fun invoke(
        number1: String,
        number2: String,
        operation: CalculatorOperation
    ): String {
        val num1 = number1.toDoubleOrNull() ?: return "Error"
        val num2 = number2.toDoubleOrNull() ?: return "Error"

        val result = when(operation) {
            is CalculatorOperation.Add -> num1 + num2
            is CalculatorOperation.Subtract -> num1 - num2
            is CalculatorOperation.Multiply -> num1 * num2
            is CalculatorOperation.Divide -> if (num2 != 0.0) num1 / num2 else return "Error"
        }

        return if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}