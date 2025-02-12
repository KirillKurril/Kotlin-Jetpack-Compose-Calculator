package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class RegisterUseCase (private val repository: PassKeyRepository) {
    operator fun invoke(password : String, pin : String) {
        repository.savePin(pin)
        repository.savePassword(password)
    }
}