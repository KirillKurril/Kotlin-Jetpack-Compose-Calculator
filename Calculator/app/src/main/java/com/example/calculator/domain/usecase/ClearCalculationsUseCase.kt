package com.example.calculator.domain.usecase

import com.example.calculator.domain.repository.CalculationRepository

class ClearCalculationsUseCase(private val repository:CalculationRepository) {
    suspend operator fun invoke() {
        return repository.clearCalculations()
    }
}