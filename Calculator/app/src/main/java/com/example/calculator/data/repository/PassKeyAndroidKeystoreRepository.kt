package com.example.calculator.data.repository

import com.example.calculator.domain.repository.PassKeyRepository
import com.example.calculator.domain.servicesInterfaces.PassKeyProvider

class PassKeyAndroidKeystoreRepository(
    passKeyProvider: PassKeyProvider
)
    : PassKeyRepository {
}