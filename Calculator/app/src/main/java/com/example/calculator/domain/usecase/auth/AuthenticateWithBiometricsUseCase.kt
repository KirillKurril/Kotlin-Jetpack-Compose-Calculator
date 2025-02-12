package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.servicesInterfaces.BiometricsProvider

class AuthenticateWithBiometricsUseCase
    (private val biometricsProvider: BiometricsProvider)
{
    suspend operator fun invoke(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        return biometricsProvider.authenticateBiometric(onSuccess, onFailure)
    }
}

