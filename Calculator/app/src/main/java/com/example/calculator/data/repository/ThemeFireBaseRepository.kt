package com.example.calculator.data.repository

import com.example.calculator.domain.model.colorScheme.ThemeType
import com.example.calculator.domain.repository.ThemeRepository
import com.example.calculator.domain.servicesInterfaces.ThemeProvider

class ThemeFireBaseRepository(
    private val themeProvider: ThemeProvider
)
    : ThemeRepository
{
    override suspend fun getTheme(): ThemeType
    {
        return themeProvider.fetchTheme()
    }
    override suspend fun saveCalculation(theme : ThemeType)
    {
        themeProvider.saveTheme(theme)
    }
}