package com.example.calculator.domain.servicesInterfaces

import android.app.Activity
import androidx.fragment.app.FragmentActivity

interface BiometricsProvider {
    fun areBiometricsEnabled(): Boolean
    fun authenticateBiometric(currentActivity: FragmentActivity, onSuccess: () -> Unit, onFailure: (String) -> Unit)
}