package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.CalculatorOperation
import kotlin.math.*
import javax.inject.Inject

class CalculateUseCase @Inject constructor() {

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
            is CalculatorOperation.Sin -> sin(num1)
            is CalculatorOperation.Cos -> cos(num1)
            is CalculatorOperation.Tan -> tan(num1)
            is CalculatorOperation.Cot -> 1.0 / tan(num1)
            is CalculatorOperation.Sqrt -> if (num1 >= 0.0) sqrt(num1) else return "Error"
            is CalculatorOperation.Parentheses -> num1
        }

        return if (result % 1.0 == 0.0) {
            result.toInt().toString()
        } else {
            result.toString()
        }
    }
}