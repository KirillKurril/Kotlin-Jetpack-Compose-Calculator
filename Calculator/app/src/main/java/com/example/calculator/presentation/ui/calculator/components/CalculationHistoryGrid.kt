package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.Calculation
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

@Composable
fun CalculationHistoryGrid(calculations: List<Calculation>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Calculation History", style = MaterialTheme.typography.titleLarge)

        LazyColumn {
            items(calculations) { calculation ->
                CalculationItem(calculation)
            }
        }
    }
}

