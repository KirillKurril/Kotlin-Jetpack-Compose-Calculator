package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.domain.usecase.auth.AreBiometricAvailableUseCase
import com.example.calculator.domain.usecase.auth.RegisterUseCase
import com.example.calculator.domain.usecase.auth.SetBiometricsPermissionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val areBiometricsAvailable: AreBiometricAvailableUseCase,
    private val setBiometricPermission: SetBiometricsPermissionUseCase,
    private val register: RegisterUseCase
) : ViewModel() {

    val navigateTo = mutableStateOf<String?>(null)

    private val _isBiometricAvailable = mutableStateOf<Boolean>(false)
    val isBiometricAvailable: State<Boolean> get() = _isBiometricAvailable

    private val _isPasswordMatch = mutableStateOf<Boolean>(true)
    val isPasswordMatch: State<Boolean> get() = _isPasswordMatch

    private val _isPinMatch = mutableStateOf<Boolean>(true)
    val isPinMatch: State<Boolean> get() = _isPinMatch

    private val _registrationError = mutableStateOf<String?>(null)
    val registrationError: State<String?> get() = _registrationError

    init {
        viewModelScope.launch {
            val isAvailable = areBiometricsAvailable.invoke()
            _isBiometricAvailable.value = isAvailable
        }
    }

    fun checkPasswordMatch(password: String, confirmPassword: String) {
        _isPasswordMatch.value = password == confirmPassword
    }

    fun checkPinMatch(pin: String, confirmPin: String) {
        _isPinMatch.value = pin == confirmPin
    }

    fun onSetBiometricsPermissionGranted() {
        viewModelScope.launch {
            setBiometricPermission.invoke(true)
        }
    }

    fun onRegister(password: String, confirmPassword: String, pin: String, confirmPin: String, useBiometrics: Boolean) {
        if (!_isPasswordMatch.value) {
            _registrationError.value = "Passwords do not match!"
            return
        }

        if (!_isPinMatch.value) {
            _registrationError.value = "PIN codes do not match!"
            return
        }

        viewModelScope.launch {
            try {
                register.invoke(password, pin)
                navigateTo.value = "login"
            } catch (e: Exception) {
                _registrationError.value = "Registration failed: ${e.message}"
            }
        }
    }

    fun onBack() {
        viewModelScope.launch {
            navigateTo.value = "login"
        }
    }
}
