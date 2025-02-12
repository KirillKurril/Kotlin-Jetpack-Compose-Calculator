package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.servicesInterfaces.CacheProvider

class CheckBiometricsPermissionUseCase(private val cacheProvider: CacheProvider) {
    operator fun invoke() : Boolean {
        return cacheProvider.isBiometricPermissionGranted()
    }
}