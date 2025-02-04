package com.example.calculator.domain.servicesInterfaces

import com.example.calculator.domain.model.colorScheme.ThemeType

interface ThemeProvider {
    suspend fun fetchTheme() : ThemeType
    suspend fun saveTheme(theme: ThemeType)
}