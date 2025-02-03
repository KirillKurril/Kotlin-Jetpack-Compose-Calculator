package com.example.calculator.domain.model 

data class Calculation(
    val id: String,
    val expression: String = "",
    val result: String = "",
    val currentNumber: String = "",
)