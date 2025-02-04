package com.example.calculator.data.repository

import com.example.calculator.domain.model.Calculation
import com.example.calculator.domain.repository.CalculationRepository
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider

class CalculationFireBaseRepository(
    private val calculationsProvider: CalculationsProvider)
    : CalculationRepository {
    override suspend fun getCalculations(): List<Calculation> {
        return calculationsProvider.fetchCalculations()
    }

    override suspend fun saveCalculation(calculation: Calculation) {
        calculationsProvider.uploadCalculation(calculation)
    }
}
