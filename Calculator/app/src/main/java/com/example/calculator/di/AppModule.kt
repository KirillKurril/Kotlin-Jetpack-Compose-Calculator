package com.example.calculator.di

import android.app.Application
import com.example.calculator.data.preferences.PreferencesManager
import com.example.calculator.data.repository.CalculationFireBaseRepository
import com.example.calculator.domain.repository.CalculationRepository
import com.example.calculator.data.repository.ThemeFireBaseRepository
import com.example.calculator.domain.repository.ThemeRepository 
import com.example.calculator.domain.usecase.calculations.GetCalculationsUseCase
import com.example.calculator.domain.usecase.calculations.SaveCalculationsUseCase
import com.example.calculator.domain.usecase.theme.GetThemeUseCase
import com.example.calculator.domain.usecase.theme.SaveThemeUseCase
import android.content.Context
import com.example.calculator.data.api.BiometricsContextProvider
import com.example.calculator.data.repository.PassKeyAndroidKeystoreRepository
import com.example.calculator.data.api.CalculationsFireBaseProvider
import com.example.calculator.data.api.PassKeyAndroidKeystoreProvider
import com.example.calculator.data.api.ThemeFireBaseProvider
import com.example.calculator.domain.repository.PassKeyRepository
import com.example.calculator.domain.servicesInterfaces.BiometricsProvider
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider
import com.example.calculator.domain.servicesInterfaces.PassKeyProvider
import com.example.calculator.domain.servicesInterfaces.ThemeProvider
import com.example.calculator.domain.usecase.auth.AuthenticateWithBiometricsUseCase
import com.example.calculator.domain.usecase.auth.CheckBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.EnableBiometricAuthUseCase
import com.example.calculator.domain.usecase.auth.ResetPassKeyUseCase
import com.example.calculator.domain.usecase.auth.SetPassKeyUseCase
import com.example.calculator.domain.usecase.auth.ValidatePassKeyUseCase
import com.example.calculator.domain.usecase.calculations.ClearCalculationsUseCase
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
    fun providePassKeyProvider(
    ): PassKeyProvider {
        return PassKeyAndroidKeystoreProvider()
    }

    @Provides
    @Singleton
    fun provideBiometricsProvider(
    ): BiometricsProvider {
        return BiometricsContextProvider()
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
    fun providePassKeyRepository(
        passKeyProvider: PassKeyProvider
    ): PassKeyRepository {
        return PassKeyAndroidKeystoreRepository(passKeyProvider)
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

    @Provides
    @Singleton
    fun provideResetPassUseCase(
        repository: PassKeyRepository
    ):  ResetPassKeyUseCase {
        return ResetPassKeyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetPassKeyUseCase(
        repository: PassKeyRepository
    ): SetPassKeyUseCase {
        return SetPassKeyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideValidatePassUseCase(
        repository: PassKeyRepository
    ): ValidatePassKeyUseCase {
        return ValidatePassKeyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideAuthenticateWithBiometricsUseCase(
        biometricsProvider: BiometricsProvider
    ): AuthenticateWithBiometricsUseCase {
        return AuthenticateWithBiometricsUseCase(biometricsProvider)
    }

    @Provides
    @Singleton
    fun provideEnableBiometricAuthUseCase(
        preferencesManager: PreferencesManager
    ): EnableBiometricAuthUseCase {
        return EnableBiometricAuthUseCase(preferencesManager)
    }

    @Provides
    @Singleton
    fun provideCheckBiometricsPermissionUseCase(
        preferencesManager: PreferencesManager
    ): CheckBiometricsPermissionUseCase {
        return CheckBiometricsPermissionUseCase(preferencesManager)
    }
}