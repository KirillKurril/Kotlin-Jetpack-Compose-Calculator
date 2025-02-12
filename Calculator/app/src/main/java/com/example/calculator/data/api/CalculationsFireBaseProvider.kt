package com.example.calculator.data.api

import android.content.ContentValues.TAG
import android.util.Log
import com.example.calculator.domain.model.Calculation
import com.example.calculator.domain.servicesInterfaces.CacheProvider
import com.google.firebase.firestore.CollectionReference
import com.example.calculator.domain.servicesInterfaces.CalculationsProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class CalculationsFireBaseProvider(
    private val cacheProvider: CacheProvider
) : CalculationsProvider
{
    private val calculationsCollection : CollectionReference
    private val fs = Firebase.firestore
    init {
        val clientDocRef = fs
            .collection("users")
            .document(cacheProvider.getClientId())
        calculationsCollection =  clientDocRef.collection("calculations")
    }

    override suspend fun fetchCalculations(): List<Calculation> {
        return try {
            val snapshot = calculationsCollection
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .await()
            val calculations = snapshot.toObjects(Calculation::class.java)
            Log.d("CalculationsProvider", "Deserialized calculations: $calculations")
            calculations
        } catch (exception: Exception) {
            Log.e("CalculationsProvider", "Ошибка: ${exception.message}")
            emptyList()
        }
    }

    override suspend fun uploadCalculation(calculation: Calculation){

        calculation.timestamp = System.currentTimeMillis()

        calculationsCollection
            .add(calculation)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "Calculation added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding calculation", e) }

        calculationsCollection
            .orderBy("timestamp")
            .get()
            .addOnSuccessListener { calculations ->
                if (calculations.size() > 50) {
                    val oldest = calculations.documents.first()
                    oldest.reference.delete()
                }
            }
    }

    override suspend fun clearCalculations() {
        calculationsCollection
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val documentReference = calculationsCollection.document(document.id)

                    documentReference.delete()
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