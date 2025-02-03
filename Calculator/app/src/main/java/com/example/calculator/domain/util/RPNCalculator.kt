package com.example.calculator.domain.util

import java.util.*
import kotlin.math.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

public class DivisionByZeroException : Exception("Деление на ноль невозможно")

class RPNCalculator {
    companion object {
        private val functions = setOf("sin", "cos", "tan", "cot", "sqrt")

        private fun countSignificantDigits(str: String): Int {
            return str.replace(Regex("[^0-9]"), "").length
        }

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
                        while (i < expression.length && 
                              (expression[i].isDigit() || 
                               expression[i] == '.' || 
                               expression[i].lowercaseChar() == 'e' ||
                               (expression[i] == '+' || expression[i] == '-') && 
                               i > 0 && expression[i-1].lowercaseChar() == 'e')) {
                            number += expression[i]
                            i++
                        }
                        tokens.add(number)
                        i--
                    }
                    expression[i] == '-' && (i == 0 || expression[i-1] == '(' || tokens.last() in listOf("+", "-", "*", "/", "(")) -> {
                        var number = "-"
                        i++
                        while (i < expression.length && 
                              (expression[i].isDigit() || 
                               expression[i] == '.' ||
                               expression[i].lowercaseChar() == 'e' ||
                               (expression[i] == '+' || expression[i] == '-') && 
                               i > 0 && expression[i-1].lowercaseChar() == 'e')) {
                            number += expression[i]
                            i++
                        }
                        tokens.add(number)
                        i--
                    }
                    expression[i].isLetter() -> {
                        var function = ""
                        while (i < expression.length && expression[i].isLetter()) {
                            function += expression[i]
                            i++
                        }
                        if (function !in functions) {
                            throw IllegalArgumentException("Unknown function: $function")
                        }
                        tokens.add(function)
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
                    token in functions -> operators.push(token)
                    token == "(" -> operators.push(token)
                    token == ")" -> {
                        while (operators.isNotEmpty() && operators.peek() != "(") {
                            output.add(operators.pop())
                        }
                        if (operators.isNotEmpty() && operators.peek() == "(") {
                            operators.pop()
                            if (operators.isNotEmpty() && operators.peek() in functions) {
                                output.add(operators.pop())
                            }
                        }
                    }
                    token in precedence -> {
                        while (operators.isNotEmpty() && operators.peek() != "(" &&
                            operators.peek() !in functions &&
                            precedence.getOrDefault(operators.peek(), 0) >= precedence.getValue(token)) {
                            output.add(operators.pop())
                        }
                        operators.push(token)
                    }
                }
            }

            while (operators.isNotEmpty()) {
                val op = operators.pop()
                if (op == "(") {
                    throw IllegalArgumentException("Mismatched parentheses")
                }
                output.add(op)
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
                            "/" -> if (b == 0.0) throw DivisionByZeroException()
                                  else a / b
                            "%" -> a * (b / 100.0)
                            else -> throw IllegalArgumentException("Unknown operator: $token")
                        }
                        stack.push(result)
                    }
                    token in functions -> {
                        if (stack.isEmpty()) throw IllegalArgumentException("Invalid expression")
                        val a = stack.pop()
                        val result = when (token) {
                            "sin" -> sin(a)
                            "cos" -> cos(a)
                            "tan" -> tan(a)
                            "cot" -> 1.0 / tan(a)
                            "sqrt" -> if (a < 0) throw IllegalArgumentException("Cannot calculate square root of negative number")
                                     else sqrt(a)
                            else -> throw IllegalArgumentException("Unknown function: $token")
                        }
                        stack.push(result)
                    }
                }
            }

            if (stack.size != 1) throw IllegalArgumentException("Invalid expression")
            return stack.pop()
        }

        fun formatResult(number: Double): String {
            val absNumber = abs(number)
            val exponent = if (absNumber > 0) log10(absNumber).toInt() else 0

            if (absNumber >= 1e15 || (absNumber < 1e-10 && number != 0.0) || abs(exponent) > 10) {
                return String.format(Locale.US, "%.10e", number)
            }

            val symbols = DecimalFormatSymbols(Locale.US)
            val format = DecimalFormat().apply {
                decimalFormatSymbols = symbols
                maximumFractionDigits = 15
                minimumFractionDigits = 0
                isGroupingUsed = false
            }

            val formatted = format.format(number)
            val significantDigits = countSignificantDigits(formatted)

            return if (significantDigits > 15) {
                String.format(Locale.US, "%.10e", number)
            } else {
                formatted
            }
        }

        fun isOperator(char: Char): Boolean {
            return char in listOf('+', '-', '*', '/', '%')
        }
    }
}
