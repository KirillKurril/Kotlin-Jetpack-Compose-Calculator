package com.example.calculator.domain.usecase.theme

import com.example.calculator.domain.model.colorScheme.ThemeType
import com.example.calculator.domain.repository.ThemeRepository

class SaveThemeUseCase(private val repository: ThemeRepository) {
    suspend operator fun invoke(theme: ThemeType) {
        repository.saveCalculation(theme)
    }
}
