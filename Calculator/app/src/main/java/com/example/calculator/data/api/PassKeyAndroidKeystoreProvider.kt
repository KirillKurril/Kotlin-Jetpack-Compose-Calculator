package com.example.calculator.data.api

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import java.security.KeyStore

class PassKeyAndroidKeystoreProvider(private val context: Context) {

    private val keyAlias = "PassKey"
    private val pinKeyAlias = "PinKey"
    private val keyStore: KeyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    // Безопасное хранилище EncryptedSharedPreferences
    private val sharedPreferences = EncryptedSharedPreferences.create(
        "secure_prefs",
        MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    init {
        if (!keyStore.containsAlias(keyAlias)) {
            generateKey(keyAlias)
        }
        if (!keyStore.containsAlias(pinKeyAlias)) {
            generateKey(pinKeyAlias)
        }
    }

    private fun generateKey(alias: String) {
        val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keySpec = KeyGenParameterSpec.Builder(
            alias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()
        keyGenerator.init(keySpec)
        keyGenerator.generateKey()
    }

    fun encryptAndSavePassword(passKey: String) {
        val (iv, encryptedData) = encrypt(passKey, keyAlias)
        saveToSharedPreferences("password", iv, encryptedData)
    }

    fun decryptPassword(): String? {
        return decrypt("password", keyAlias)
    }

    fun encryptAndSavePin(pin: String) {
        val (iv, encryptedData) = encrypt(pin, pinKeyAlias)
        saveToSharedPreferences("pin", iv, encryptedData)
    }

    fun decryptPin(): String? {
        return decrypt("pin", pinKeyAlias)
    }

    private fun encrypt(data: String, alias: String): Pair<ByteArray, ByteArray> {
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, getKey(alias))
        val encryption = cipher.doFinal(data.toByteArray(Charsets.UTF_8))
        return Pair(cipher.iv, encryption)
    }

    private fun decrypt(keyPrefix: String, alias: String): String? {
        val (iv, encryptedData) = getFromSharedPreferences(keyPrefix) ?: return null
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, getKey(alias), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encryptedData), Charsets.UTF_8)
    }

    private fun getKey(alias: String): SecretKey {
        return (keyStore.getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey
    }

    private fun saveToSharedPreferences(keyPrefix: String, iv: ByteArray, encryptedData: ByteArray) {
        sharedPreferences.edit()
            .putString("${keyPrefix}_iv", Base64.encodeToString(iv, Base64.DEFAULT))
            .putString("${keyPrefix}_data", Base64.encodeToString(encryptedData, Base64.DEFAULT))
            .apply()
    }

    private fun getFromSharedPreferences(keyPrefix: String): Pair<ByteArray, ByteArray>? {
        val iv = sharedPreferences.getString("${keyPrefix}_iv", null)?.let { Base64.decode(it, Base64.DEFAULT) }
        val data = sharedPreferences.getString("${keyPrefix}_data", null)?.let { Base64.decode(it, Base64.DEFAULT) }
        return if (iv != null && data != null) Pair(iv, data) else null
    }

    fun isPasswordValid(enteredPassword: String): Boolean {
        return enteredPassword == decryptPassword()
    }

    fun isPinValid(enteredPin: String): Boolean {
        return enteredPin == decryptPin()
    }

    fun clear() {
        keyStore.deleteEntry(keyAlias)
        keyStore.deleteEntry(pinKeyAlias)
        sharedPreferences.edit().clear().apply()
    }
}
