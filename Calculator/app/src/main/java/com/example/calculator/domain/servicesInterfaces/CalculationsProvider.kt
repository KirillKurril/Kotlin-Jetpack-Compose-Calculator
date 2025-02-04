package com.example.calculator.domain.servicesInterfaces

import com.example.calculator.domain.model.Calculation

interface CalculationsProvider {
    suspend fun fetchCalculations() : List<Calculation>
    suspend fun uploadCalculation(calculation: Calculation)
    suspend fun clearCalculations()
}