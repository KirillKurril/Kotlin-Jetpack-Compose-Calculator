package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.calculator.domain.usecase.auth.AuthenticateWithBiometricsUseCase
import com.example.calculator.domain.usecase.auth.CheckBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.ResetPasswordUseCase
import com.example.calculator.domain.usecase.auth.ValidatePinUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val checkBiometricPermission: CheckBiometricsPermissionUseCase,
    private val authWithBiometrics: AuthenticateWithBiometricsUseCase,
    private val validatePin: ValidatePinUseCase,
    private val resetPassword: ResetPasswordUseCase
) : ViewModel() {

    val navigateTo = mutableStateOf<String?>(null)

    private val _isBiometricAllow = mutableStateOf<Boolean>(false)
    val isBiometricAllow: State<Boolean> get() = _isBiometricAllow

    private val _accessConfirmed = mutableStateOf<Boolean>(false)
    val accessConfirmed: State<Boolean> get() = _accessConfirmed

    private val _resetError = mutableStateOf<String?>(null)
    val resetError: State<String?> get() = _resetError

    init {
        viewModelScope.launch {
            try {
                val result = checkBiometricPermission.invoke()
                _isBiometricAllow.value = result
            } catch (e: Exception) {
                _resetError.value = "Error checking biometric permission: ${e.message}"
            }
        }
    }

    private fun accessConfirmedSuccessfully() {
        _accessConfirmed.value = true
    }

    private fun accessFailure(error: String) {
        _resetError.value = error
    }

    fun onFingerprintCheck() {
        viewModelScope.launch {
            try {
                authWithBiometrics.invoke(
                    { accessConfirmedSuccessfully() },
                    { error -> accessFailure(error) }
                )
            } catch (e: Exception) {
                accessFailure("Biometric authentication failed: ${e.message}")
            }
        }
    }

    fun onPinVerification(pin: String) {
        viewModelScope.launch {
            try {
                val result = validatePin.invoke(pin)
                if (!result) {
                    accessFailure("Pin code is incorrect!")
                } else {
                    accessConfirmedSuccessfully()
                }
            } catch (e: Exception) {
                accessFailure("Error verifying pin: ${e.message}")
            }
        }
    }

    fun checkPasswordMatch(firstPassword: String, secondPassword: String): Boolean {
        return firstPassword == secondPassword
    }

    fun onSetNewPassword(newPassword: String) {
        viewModelScope.launch {
            try {
                resetPassword.invoke(newPassword)
                navigateTo.value = "calculator"
            } catch (e: Exception) {
                _resetError.value = "Failed to reset password: ${e.message}"
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            navigateTo.value = "login"
        }
    }

    fun clearError() {
        _resetError.value = null
    }
}
