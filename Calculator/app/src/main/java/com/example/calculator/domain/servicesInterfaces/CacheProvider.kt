package com.example.calculator.domain.servicesInterfaces

interface CacheProvider {
    fun setBiometricPermission(isGranted: Boolean)
    fun isBiometricPermissionGranted(): Boolean
    fun getClientId(): String
}
