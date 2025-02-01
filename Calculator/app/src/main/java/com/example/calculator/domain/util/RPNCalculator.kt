package com.example.calculator.domain.util

import java.util.*
import kotlin.math.abs

class RPNCalculator {
    companion object {
        fun evaluate(expression: String): Double {
            val tokens = tokenize(expression)
            val postfix = infixToPostfix(tokens)
            return evaluatePostfix(postfix)
        }

        private fun tokenize(expression: String): List<String> {
            val tokens = mutableListOf<String>()
            var i = 0
            while (i < expression.length) {
                when {
                    expression[i].isDigit() || expression[i] == '.' -> {
                        var number = ""
                        while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                            number += expression[i]
                            i++
                        }
                        tokens.add(number)
                        i--
                    }
                    expression[i] == '-' && (i == 0 || expression[i-1] == '(' || tokens.last() in listOf("+", "-", "*", "/", "(")) -> {
                        var number = "-"
                        i++
                        while (i < expression.length && (expression[i].isDigit() || expression[i] == '.')) {
                            number += expression[i]
                            i++
                        }
                        tokens.add(number)
                        i--
                    }
                    expression[i] in listOf('+', '-', '*', '/', '(', ')', '%') -> tokens.add(expression[i].toString())
                    !expression[i].isWhitespace() -> throw IllegalArgumentException("Invalid character: ${expression[i]}")
                }
                i++
            }
            return tokens
        }

        private fun infixToPostfix(tokens: List<String>): List<String> {
            val output = mutableListOf<String>()
            val operators = Stack<String>()
            val precedence = mapOf(
                "+" to 1, "-" to 1,
                "*" to 2, "/" to 2,
                "%" to 2
            )

            for (token in tokens) {
                when {
                    token.toDoubleOrNull() != null -> output.add(token)
                    token == "(" -> operators.push(token)
                    token == ")" -> {
                        while (operators.isNotEmpty() && operators.peek() != "(") {
                            output.add(operators.pop())
                        }
                        if (operators.isNotEmpty() && operators.peek() == "(") {
                            operators.pop()
                        }
                    }
                    token in precedence -> {
                        while (operators.isNotEmpty() && operators.peek() != "(" &&
                            precedence.getOrDefault(operators.peek(), 0) >= precedence.getValue(token)) {
                            output.add(operators.pop())
                        }
                        operators.push(token)
                    }
                }
            }

            while (operators.isNotEmpty()) {
                if (operators.peek() == "(") {
                    throw IllegalArgumentException("Mismatched parentheses")
                }
                output.add(operators.pop())
            }

            return output
        }

        private fun evaluatePostfix(tokens: List<String>): Double {
            val stack = Stack<Double>()

            for (token in tokens) {
                when {
                    token.toDoubleOrNull() != null -> stack.push(token.toDouble())
                    token in listOf("+", "-", "*", "/", "%") -> {
                        if (stack.size < 2) throw IllegalArgumentException("Invalid expression")
                        val b = stack.pop()
                        val a = stack.pop()
                        val result = when (token) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> if (b == 0.0) throw IllegalArgumentException("Division by zero")
                                  else a / b
                            "%" -> a * (b / 100.0)
                            else -> throw IllegalArgumentException("Unknown operator: $token")
                        }
                        stack.push(result)
                    }
                }
            }

            if (stack.size != 1) throw IllegalArgumentException("Invalid expression")
            return stack.pop()
        }

        fun formatResult(number: Double): String {
            return when {
                abs(number) >= 1e10 || (abs(number) < 1e-10 && number != 0.0) -> 
                    String.format("%.2e", number)
                else -> {
                    val formatted = String.format("%.10f", number).trimEnd('0').trimEnd('.')
                    if (formatted.length > 10) String.format("%.2e", number)
                    else formatted
                }
            }
        }
    }
}
