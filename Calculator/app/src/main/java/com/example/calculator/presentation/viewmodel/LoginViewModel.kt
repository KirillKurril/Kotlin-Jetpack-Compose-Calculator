package com.example.calculator.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.usecase.auth.AuthenticateWithBiometricsUseCase
import com.example.calculator.domain.usecase.auth.CheckBiometricsPermissionUseCase
import com.example.calculator.domain.usecase.auth.ValidatePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validatePassKey : ValidatePasswordUseCase,
    private val authWithBiometrics : AuthenticateWithBiometricsUseCase,
    private val checkBiometricsPermission: CheckBiometricsPermissionUseCase
) : ViewModel()
{

}