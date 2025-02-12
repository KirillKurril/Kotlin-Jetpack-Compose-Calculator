package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class ResetPasswordUseCase (private val repository: PassKeyRepository) {
    operator fun invoke(password : String) {
        repository.resetPassword(password)
    }
}