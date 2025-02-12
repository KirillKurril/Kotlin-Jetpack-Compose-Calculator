package com.example.calculator.domain.servicesInterfaces

interface PassKeyProvider {
    fun encryptAndSavePassword(passKey: String)
    fun encryptAndSavePin(pin: String)
    fun isPasswordValid(enteredPassword: String): Boolean
    fun isPinValid(enteredPin: String): Boolean
    fun clear()
}