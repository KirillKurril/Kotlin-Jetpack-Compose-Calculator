package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.calculator.domain.model.Calculation
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun CalculationHistoryGrid(
    calculations: List<Calculation>,
    onCalculationSelect: (Calculation) -> Unit
) {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = remember { screenHeight * 0.7f }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = maxHeight)
                .padding(16.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Calculation History", style = MaterialTheme.typography.titleLarge)

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(calculations) { calculation ->
                    CalculationItem(calculation, onCalculationSelect)
                }
            }
        }
    }
}