package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.servicesInterfaces.BiometricsProvider

class AreBiometricAvailableUseCase (private val biometricsProvider: BiometricsProvider) {
    operator fun invoke() : Boolean {
        return biometricsProvider.areBiometricsEnabled()
    }
}
