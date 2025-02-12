package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.servicesInterfaces.CacheProvider

class SetBiometricsPermissionUseCase(private val cacheProvider: CacheProvider) {
    operator fun invoke(isGranted : Boolean) {
        return cacheProvider.setBiometricPermission(isGranted)
    }
}