package com.example.calculator.domain.repository

interface PassKeyRepository {
    fun savePassword(password: String)

    fun isPasswordValid(enteredPassword: String): Boolean

    fun savePin(pin: String)

    fun isPinValid(enteredPin: String): Boolean

    fun resetPassword(password: String)

    fun clearData()

    fun isUserRegistered(): Boolean
}