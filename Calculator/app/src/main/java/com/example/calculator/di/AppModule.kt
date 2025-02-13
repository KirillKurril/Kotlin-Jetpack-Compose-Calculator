package com.example.calculator.di

import android.app.Application
import com.example.calculator.data.api.PreferencesProvider
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
import com.example.calculator.domain.servicesInterfaces.CacheProvider
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider
import com.example.calculator.domain.servicesInterfaces.PassKeyProvider
import com.example.calculator.domain.servicesInterfaces.ThemeProvider
import com.example.calculator.domain.usecase.auth.AuthenticateWithBiometricsUseCase
import com.example.calculator.domain.usecase.auth.CheckBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.AreBiometricAvailableUseCase
import com.example.calculator.domain.usecase.auth.CheckUserRegistredUseCase
import com.example.calculator.domain.usecase.auth.ResetPasswordUseCase
import com.example.calculator.domain.usecase.auth.SetBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.RegisterUseCase
import com.example.calculator.domain.usecase.auth.ValidatePasswordUseCase
import com.example.calculator.domain.usecase.auth.ValidatePinUseCase
import com.example.calculator.domain.usecase.calculations.ClearCalculationsUseCase
import com.example.calculator.presentation.ui.navigation.NavigationManager
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
    fun provideCacheManager(
        context: Context
    ): CacheProvider {
        return PreferencesProvider(context)
    }

    @Provides
    @Singleton
    fun provideCalculationsProvider(
        cacheProvider: CacheProvider
    ): CalculationsProvider {
        return CalculationsFireBaseProvider(cacheProvider)
    }

    @Provides
    @Singleton
    fun provideThemeProvider(
        cacheProvider: CacheProvider
    ): ThemeProvider {
        return ThemeFireBaseProvider(cacheProvider)
    }

    @Provides
    @Singleton
    fun providePassKeyProvider(
        context: Context
    ): PassKeyProvider {
        return PassKeyAndroidKeystoreProvider(context)
    }

    @Provides
    @Singleton
    fun provideBiometricsProvider(
        context: Context
    ): BiometricsProvider {
        return BiometricsContextProvider(context)
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
    ):  ResetPasswordUseCase {
        return ResetPasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetPassKeyUseCase(
        repository: PassKeyRepository
    ): RegisterUseCase {
        return RegisterUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideValidatePasswordUseCase(
        repository: PassKeyRepository
    ): ValidatePasswordUseCase {
        return ValidatePasswordUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideValidatePinUseCase(
        repository: PassKeyRepository
    ): ValidatePinUseCase {
        return ValidatePinUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckUserRegistredUseCase(
        repository: PassKeyRepository
    ): CheckUserRegistredUseCase {
        return CheckUserRegistredUseCase(repository)
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
        biometricsProvider: BiometricsProvider
    ): AreBiometricAvailableUseCase {
        return AreBiometricAvailableUseCase(biometricsProvider)
    }

    @Provides
    @Singleton
    fun provideSetBiometricsPermissionUseCase(
        cacheProvider: CacheProvider
    ): SetBiometricsPermissionUseCase {
        return SetBiometricsPermissionUseCase(cacheProvider)
    }

    @Provides
    @Singleton
    fun provideCheckBiometricsPermissionUseCase(
        cacheProvider: CacheProvider
    ): CheckBiometricsPermissionUseCase {
        return CheckBiometricsPermissionUseCase(cacheProvider)
    }

    @Provides
    @Singleton
    fun provideNavigationManager(): NavigationManager {
        return NavigationManager
    }    
}