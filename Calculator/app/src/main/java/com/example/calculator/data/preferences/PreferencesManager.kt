package com.example.calculator.data.preferences

import android.content.SharedPreferences
import java.util.UUID
import android.content.Context


class PreferencesManager(private val context: Context){
    val clientId: String
    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    init{
        val _clientId = sharedPreferences.getString("clientId", null)
        
        if(_clientId == null){
            clientId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("clientId", clientId).apply()
            }
        else
            clientId = _clientId
    }


    fun saveBiometricPermission(isGranted: Boolean) {
        sharedPreferences.edit().putBoolean("biometric_enabled", isGranted).apply()
    }

    fun isBiometricPermissionGranted(): Boolean {
        return sharedPreferences.getBoolean("biometric_enabled", false)
    }
}



