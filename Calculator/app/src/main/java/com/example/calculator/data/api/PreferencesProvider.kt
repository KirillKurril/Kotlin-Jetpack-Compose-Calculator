package com.example.calculator.data.api

import android.content.SharedPreferences
import java.util.UUID
import android.content.Context
import com.example.calculator.domain.servicesInterfaces.CacheProvider


class PreferencesProvider(private val context: Context) : CacheProvider{
    private val _clientId: String

    private val sharedPreferences : SharedPreferences =
        context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)

    init{
        var clientId = sharedPreferences.getString("clientId", null)
        
        if(clientId == null){
            clientId = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("clientId", clientId).apply()
            }

        _clientId = clientId
    }

    override fun getClientId(): String {
        return _clientId
    }

    override fun setBiometricPermission(isGranted: Boolean) {
        sharedPreferences.edit().putBoolean("biometric_enabled", isGranted).apply()
    }

    override  fun isBiometricPermissionGranted(): Boolean {
        return sharedPreferences.getBoolean("biometric_enabled", false)
    }
}



