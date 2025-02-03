package com.example.calculator.data.repository

import com.example.calculator.domain.model.Calculation
import com.example.calculator.domain.repository.CalculationRepository
import com.example.calculator.data.api.CalculationsFetchingService

class CalculationFireBaseRepository(private val fetchingService: CalculationsFetchingService) : CalculationRepository {
    override suspend fun getCalculations(): List<Calculation> {
        return fetchingService.fetchCalculations()
    }
    override suspend fun saveCalculation(calculation: Calculation) {
    }
}