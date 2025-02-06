package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.Calculation
import com.example.calculator.R

@Composable
fun CalculationHistoryGrid(
    calculations: List<Calculation>,
    onCalculationSelect: (Calculation) -> Unit,
    onCalculationClear: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Calculation History", style = MaterialTheme.typography.titleLarge)

            IconButton(onClick = onCalculationClear) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Clear History",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(calculations) { calculation ->
                CalculationItem(calculation, onCalculationSelect)
            }
        }
    }
}
