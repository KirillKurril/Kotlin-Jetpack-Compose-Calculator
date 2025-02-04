package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.colorScheme.ThemeType
import com.example.calculator.domain.repository.ThemeRepository

class GetThemeUseCase(private val repository:ThemeRepository) {
    suspend operator fun invoke(): ThemeType {
        return repository.getTheme()
    }
}