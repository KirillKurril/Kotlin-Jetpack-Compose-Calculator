package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class ValidatePasswordUseCase (private val repository: PassKeyRepository) {
    operator fun invoke(password : String) {
        repository.isPasswordValid(password)
    }
}