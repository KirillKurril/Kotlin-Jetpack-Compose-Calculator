package com.example.calculator.domain.repository

import com.example.calculator.domain.model.colorScheme.ThemeType

interface ThemeRepository {
    suspend fun getTheme(): ThemeType
    suspend fun saveCalculation(themeType: ThemeType)
}