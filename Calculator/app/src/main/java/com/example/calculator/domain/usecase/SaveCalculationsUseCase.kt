package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.Calculation
import com.example.calculator.domain.repository.CalculationRepository

class SaveCalculationsUseCase(private val repository: CalculationRepository) {
    suspend operator fun invoke(calculation: Calculation) {
        repository.saveCalculation(calculation)
    }
}