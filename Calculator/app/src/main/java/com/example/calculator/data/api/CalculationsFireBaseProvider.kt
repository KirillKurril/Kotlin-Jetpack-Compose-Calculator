package com.example.calculator.data.api

import android.content.ContentValues.TAG
import android.util.Log
import com.example.calculator.data.preferences.PreferencesManager
import com.example.calculator.domain.model.Calculation
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider
import kotlinx.coroutines.tasks.await

class CalculationsFireBaseProvider(
    private val preferencesManager: PreferencesManager
) : CalculationsProvider
{
    private val firestore = FirebaseFirestore.getInstance()
    private val calculations : CollectionReference

    init {
        firestore
        val clientRef = firestore
            .collection("users")
            .document(preferencesManager.clientId)
        calculations =  clientRef.collection("calculations")
    }

    override suspend fun fetchCalculations() : List<Calculation> {
        return try {
            val snapshots = calculations
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            snapshots.documents.mapNotNull { document ->
                document.toObject(Calculation::class.java)
            }
        } catch (exception: Exception) {
            Log.e("CalculationsProvider", "Ошибка: ${exception.message}")
            emptyList()
        }
    }

    override suspend fun uploadCalculation(calculation: Calculation){

        calculation.timestamp = System.currentTimeMillis()

        calculations
            .add(calculation)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Calculation added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding calculation", e) }

        calculations
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { snapshots ->
                if (snapshots.size() > 50) {
                    val oldest = snapshots.documents.first()
                    oldest.reference.delete()
                }
            }
    }

    override suspend fun clearCalculations(){

        calculations
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    firestore.document(document.id).delete()
                        .addOnSuccessListener {
                            Log.d(TAG, "Document ${document.id} successfully deleted!")
                        }
                        .addOnFailureListener { e ->
                            Log.w(TAG, "Error deleting document ${document.id}", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error getting documents: ", e)
            }
    }
}