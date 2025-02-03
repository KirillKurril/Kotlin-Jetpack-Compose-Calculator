package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.Calculation;
import com.example.calculator.domain.repository.CalculationRepository;

class GetCalculationsUseCase(private val repository:CalculationRepository) {
    suspend operator fun invoke(): List<Calculation> {
        return repository.getCalculations()
    }
}