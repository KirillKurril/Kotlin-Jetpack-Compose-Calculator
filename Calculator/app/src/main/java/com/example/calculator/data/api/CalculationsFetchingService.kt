package com.example.calculator.data.api

import com.example.calculator.domain.model.Calculation
import com.google.firebase.firestore.FirebaseFirestore

class CalculationsFetchingService {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun fetchCalculations(): List<Calculation> {
        var calculations = listOf<Calculation>()

        calculations = listOf(
            Calculation(id = "1", expression = "2 + 2", result = "4", currentNumber = ""),
            Calculation(id = "2", expression = "3 * 5", result = "15", currentNumber = "")
        )

        return calculations
    }
}