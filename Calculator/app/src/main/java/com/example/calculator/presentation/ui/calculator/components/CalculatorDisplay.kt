package com.example.calculator.presentation.ui.calculator.components

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.domain.state.CalculatorState
import com.example.calculator.domain.model.CalculatorAction

@Composable
fun CalculatorDisplay(
    state: CalculatorState,
    onAction: (CalculatorAction) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    Box(
        modifier = modifier
            .fillMaxHeight(0.4f)
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .pointerInput(Unit) {
                detectHorizontalDragGestures { _, dragAmount ->
                    when {
                        dragAmount > 0 -> onAction(CalculatorAction.ClearAll) // свайп вправо
                        dragAmount < 0 -> onAction(CalculatorAction.Backspace) // свайп влево
                    }
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            if (state.expression.isNotEmpty()) {
                Text(
                    text = state.expression,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 32.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.End,
                    maxLines = Int.MAX_VALUE
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            if (state.result.isNotEmpty()) {
                Text(
                    text = "= ${state.result}",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

            if (state.errorMessage != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = state.errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }
        }
    }
}