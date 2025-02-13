package com.example.calculator.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calculator.domain.usecase.auth.AuthenticateWithBiometricsUseCase
import com.example.calculator.domain.usecase.auth.CheckBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.ValidatePasswordUseCase
import com.example.calculator.presentation.ui.navigation.NavigationManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val checkBiometricsPermission: CheckBiometricsPermissionUseCase,
    private val authWithBiometrics: AuthenticateWithBiometricsUseCase,
    private val validatePassword: ValidatePasswordUseCase,
    private val navigationManager: NavigationManager
) : ViewModel() {

    private val _isBiometricAllow = mutableStateOf<Boolean>(false)
    val isBiometricAllow: State<Boolean> get() = _isBiometricAllow

    private val _accessConfirmed = mutableStateOf<Boolean>(false)
    val accessConfirmed: State<Boolean> get() = _accessConfirmed

    private val _loginError = mutableStateOf<String?>(null)
    val loginError: State<String?> get() = _loginError

    init {
        viewModelScope.launch {
            _isBiometricAllow.value = checkBiometricsPermission.invoke()
        }
    }

    private fun accessConfirmedSuccessfully() {
        _accessConfirmed.value = true
        navigationManager.navigate("calculator")
    }

    private fun accessFailure(error: String) {
        _loginError.value = error
    }

    fun onFingerprintCheck() {
        viewModelScope.launch {
            authWithBiometrics.invoke(
                { accessConfirmedSuccessfully() },
                { error -> accessFailure(error) }
            )
        }
    }

    fun onPasswordLogin(password: String) {
        viewModelScope.launch {
            val isValid = validatePassword.invoke(password)
            if (isValid) {
                accessConfirmedSuccessfully()
            } else {
                accessFailure("Invalid password. Please try again.")
            }
        }
    }

    fun onSignUp()
    {
        viewModelScope.launch {
            navigationManager.navigate("registration")
        }
    }

    fun onResetPassword()
    {
        viewModelScope.launch {
            navigationManager.navigate("reset-password")
        }
    }
}
