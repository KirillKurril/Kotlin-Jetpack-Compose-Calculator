package com.example.calculator.domain.repository

import com.example.calculator.domain.model.Calculation

interface CalculationRepository {
    suspend fun getCalculations(): List<Calculation>
    suspend fun saveCalculation(calculation: Calculation)
    suspend fun clearCalculations()
}