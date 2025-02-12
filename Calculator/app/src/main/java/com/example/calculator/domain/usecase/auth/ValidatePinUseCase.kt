package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class ValidatePinUseCase (private val repository: PassKeyRepository) {
    operator fun invoke(pin : String) : Boolean {
        return repository.isPinValid(pin)
    }
}