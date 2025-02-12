package com.example.calculator.domain.usecase.calculations

import com.example.calculator.domain.repository.CalculationRepository

class ClearCalculationsUseCase(private val repository:CalculationRepository) {
    suspend operator fun invoke() {
        return repository.clearCalculations()
    }
}