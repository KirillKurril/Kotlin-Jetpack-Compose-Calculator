package com.example.calculator.data.repository

import com.example.calculator.domain.repository.PassKeyRepository
import com.example.calculator.domain.servicesInterfaces.PassKeyProvider

class PassKeyAndroidKeystoreRepository(
    private val passKeyProvider: PassKeyProvider
) : PassKeyRepository
{
    override fun savePassword(password: String) {
        passKeyProvider.encryptAndSavePassword(password)
    }

    override fun isPasswordValid(enteredPassword: String): Boolean {
        return passKeyProvider.isPasswordValid(enteredPassword)
    }

    override fun savePin(pin: String) {
        passKeyProvider.encryptAndSavePin(pin)
    }

    override fun isPinValid(enteredPin: String): Boolean {
        return passKeyProvider.isPinValid(enteredPin)
    }

    override fun clearData() {
        passKeyProvider.clear()
    }
}