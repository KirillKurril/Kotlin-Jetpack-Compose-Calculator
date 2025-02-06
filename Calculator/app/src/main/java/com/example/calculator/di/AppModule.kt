package com.example.calculator.di

import android.app.Application
import com.example.calculator.data.preferences.PreferencesManager
import com.example.calculator.data.repository.CalculationFireBaseRepository
import com.example.calculator.domain.repository.CalculationRepository
import com.example.calculator.data.repository.ThemeFireBaseRepository
import com.example.calculator.domain.repository.ThemeRepository 
import com.example.calculator.domain.usecase.GetCalculationsUseCase
import com.example.calculator.domain.usecase.SaveCalculationsUseCase
import com.example.calculator.domain.usecase.GetThemeUseCase
import com.example.calculator.domain.usecase.SaveThemeUseCase
import android.content.Context
import com.example.calculator.data.api.CalculationsFireBaseProvider
import com.example.calculator.data.api.ThemeFireBaseProvider
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider
import com.example.calculator.domain.servicesInterfaces.ThemeProvider
import com.example.calculator.domain.usecase.ClearCalculationsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(
        application: Application
    ): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun providePreferencesManager(
        context: Context
    ): PreferencesManager {
        return PreferencesManager(context)
    }

    @Provides
    @Singleton
    fun provideCalculationsProvider(
        preferencesManager: PreferencesManager
    ): CalculationsProvider {
        return CalculationsFireBaseProvider(preferencesManager)
    }

    @Provides
    @Singleton
    fun provideThemeProvider(
        preferencesManager: PreferencesManager
    ): ThemeProvider {
        return ThemeFireBaseProvider(preferencesManager)
    }

    @Provides
    @Singleton
    fun provideCalculationRepository(
        calculationsProvider: CalculationsProvider
    ): CalculationRepository {
        return CalculationFireBaseRepository(calculationsProvider)
    }

    @Provides
    @Singleton
    fun provideThemeRepository(
        themeProvider: ThemeProvider
    ): ThemeRepository {
        return ThemeFireBaseRepository(themeProvider)
    }

    @Provides
    @Singleton
    fun provideGetCalculationsUseCase(
        repository: CalculationRepository
    ): GetCalculationsUseCase {
        return GetCalculationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveCalculationsUseCase(
        repository: CalculationRepository
    ): SaveCalculationsUseCase {
        return SaveCalculationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideClearCalculationsUseCase(
        repository: CalculationRepository
    ): ClearCalculationsUseCase {
        return ClearCalculationsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetThemeUseCase(
        repository: ThemeRepository
    ): GetThemeUseCase {
        return GetThemeUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveThemeUseCase(
        repository: ThemeRepository  
    ): SaveThemeUseCase {
        return SaveThemeUseCase(repository)
    }    
}