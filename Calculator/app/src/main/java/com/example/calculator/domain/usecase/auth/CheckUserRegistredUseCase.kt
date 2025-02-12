package com.example.calculator.domain.usecase.auth

import com.example.calculator.domain.repository.PassKeyRepository

class CheckUserRegistredUseCase(private val repository: PassKeyRepository) {
    operator fun invoke() : Boolean {
        return repository.isUserRegistered()
    }
}