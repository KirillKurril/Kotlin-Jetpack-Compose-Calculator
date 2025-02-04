package com.example.calculator.data.api

import android.util.Log
import com.example.calculator.data.preferences.PreferencesManager
import com.example.calculator.domain.servicesInterfaces.ThemeProvider
import com.example.calculator.domain.model.colorScheme.ThemeType
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ThemeFireBaseProvider(
    private val preferencesManager: PreferencesManager
) : ThemeProvider
{
    private val firestore = FirebaseFirestore.getInstance()
    private val clientDoc : DocumentReference = firestore
        .collection("users")
        .document(preferencesManager.clientId)

    override suspend fun fetchTheme() : ThemeType {
        return try {
            val document = clientDoc.get().await()
            if (document != null && document.exists()) {
                val themeName = document.getString("theme") ?: ThemeType.LIGHT.name
                ThemeType.valueOf(themeName)
            } else {
                Log.w("ThemeFireBaseProvider", "Нет такого документа, используем LIGHT по умолчанию")
                ThemeType.LIGHT
            }
        } catch (exception: Exception) {
            Log.e("ThemeFireBaseProvider", "Ошибка: ${exception.message}")
            ThemeType.LIGHT
        }
    }
    

    override suspend fun saveTheme(theme: ThemeType) {
        clientDoc.update("theme", theme)
            .addOnSuccessListener { 
                Log.w("ThemeFireBaseProvider", "Тема сохранена!")
            }
            .addOnFailureListener {
                Log.e("ThemeFireBaseProvider", "Ошибка: ${it.message}")
            }
    }
    
}