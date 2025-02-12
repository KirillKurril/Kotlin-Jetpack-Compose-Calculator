package com.example.calculator.domain.servicesInterfaces

interface BiometricsProvider {
    fun areBiometricsEnabled(): Boolean
    fun authenticateBiometric(onSuccess: () -> Unit, onFailure: (String) -> Unit)
}