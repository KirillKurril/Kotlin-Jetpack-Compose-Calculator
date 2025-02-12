package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class ResetPassKeyUseCase (private val repository: PassKeyRepository) {
    operator fun invoke(password : String, pin : String) {
        repository.clearData()
        repository.savePin(pin)
        repository.savePassword(password)
    }
}