package com.example.calculator.domain.usecase.auth

import android.app.Activity
import androidx.fragment.app.FragmentActivity
import com.example.calculator.domain.servicesInterfaces.BiometricsProvider

class AuthenticateWithBiometricsUseCase
    (private val biometricsProvider: BiometricsProvider)
{
    suspend operator fun invoke(currentActivity : FragmentActivity, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        return biometricsProvider.authenticateBiometric(currentActivity, onSuccess, onFailure)
    }
}

