package com.example.calculator.data.api


import android.app.Activity
import android.content.Context
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.calculator.domain.servicesInterfaces.BiometricsProvider

class BiometricsContextProvider(
    private val context: Context,
)
    : BiometricsProvider
{
    private var _biometricsEnabled : Boolean

    init {
        val biometricManager = BiometricManager.from(context)
        _biometricsEnabled = biometricManager.canAuthenticate(
            BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
    }

    override fun areBiometricsEnabled() : Boolean
    {
        return _biometricsEnabled
    }

    override fun authenticateBiometric(onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        if (!_biometricsEnabled) {
            onFailure("Fingerprint authentication is not available on this device.")
            return
        }

        val activity = context as? FragmentActivity
        if (activity == null) {
            onFailure("Context is not a FragmentActivity")
            return
        }

        val biometricPrompt = BiometricPrompt(
            activity,
            ContextCompat.getMainExecutor(context),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    onSuccess()
                }

                override fun onAuthenticationFailed() {
                    onFailure("Authentication failed")
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    onFailure(errString.toString())
                }
            }
        )

        val biometricPromptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Biometric Login")
            .setSubtitle("Use your fingerprint to log in")
            .setDeviceCredentialAllowed(false)
            .build()

        biometricPrompt.authenticate(biometricPromptInfo)
    }
}