package com.example.calculator.data.api

import android.util.Log
import com.example.calculator.data.preferences.PreferencesManager
import com.example.calculator.domain.servicesInterfaces.ThemeProvider
import com.example.calculator.domain.model.colorScheme.ThemeType
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class ThemeFireBaseProvider(
    private val preferencesManager: PreferencesManager
) : ThemeProvider
{
    private val fs = Firebase.firestore
    private val clientDocRef = fs
        .collection("users")
        .document(preferencesManager.clientId)

    override suspend fun fetchTheme() : ThemeType {
        return try {
            val document = clientDocRef.get().await()
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
        clientDocRef.set(mapOf("theme" to theme.name), SetOptions.merge())
            .addOnSuccessListener {
                Log.w("ThemeFireBaseProvider", "Тема сохранена!")
            }
            .addOnFailureListener {
                Log.e("ThemeFireBaseProvider", "Ошибка: ${it.message}")
            }
    }
}