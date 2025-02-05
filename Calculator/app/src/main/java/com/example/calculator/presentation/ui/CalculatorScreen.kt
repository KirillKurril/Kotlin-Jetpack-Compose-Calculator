package com.example.calculator.presentation.ui
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import android.os.VibrationEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculator.domain.model.CalculatorAction
import com.example.calculator.presentation.viewmodel.CalculatorViewModel
import com.example.calculator.presentation.ui.calculator.components.CalculatorDisplay
import com.example.calculator.presentation.ui.calculator.components.CalculatorButtonsGrid
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs
import android.os.Vibrator
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.*
import androidx.compose.runtime.*
import com.example.calculator.presentation.ui.calculator.components.CalculationHistoryGrid
import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.geometry.Offset
import com.example.calculator.presentation.ui.calculator.components.ThemeSelector
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.ui.Alignment
import com.example.calculator.domain.model.Calculation
import com.example.calculator.presentation.ui.theme.CalculatorTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = hiltViewModel()
) {

    val selectedTheme by viewModel.selectedTheme.observeAsState()
    val state by viewModel.state
    var lastSwipeTime = 0L
    val minSwipeDistance = 20f
    val minTimeBetweenSwipes = 300L
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val calculations by viewModel.calculations.collectAsState()
    var showHistory by remember { mutableStateOf(false) }
    var showThemeSelector by remember { mutableStateOf(false) }
    var accumulatedDrag = Offset.Zero
    LaunchedEffect(Unit) {
        viewModel.fetchCalculations()
    }

    if (selectedTheme != null) {
        CalculatorTheme(selectedTheme!!) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { startOffset ->
                                    Log.d("SwipeGesture", "Drag started at: $startOffset")
                                },
                                onDragEnd = {
                                    val totalX = accumulatedDrag.x
                                    val totalY = accumulatedDrag.y
                                    Log.d(
                                        "SwipeGesture",
                                        "Drag ended. Total X: $totalX, Total Y: $totalY"
                                    )

                                    if (System.currentTimeMillis() - lastSwipeTime >= minTimeBetweenSwipes) {
                                        when {
                                            abs(totalX) > abs(totalY) && abs(totalX) >= minSwipeDistance -> {
                                                if (totalX > 0) {
                                                    viewModel.onAction(CalculatorAction.ClearAll)
                                                } else {
                                                    viewModel.onAction(CalculatorAction.Backspace)
                                                }
                                            }

                                            abs(totalY) >= minSwipeDistance -> {
                                                if (totalY < 0) {
                                                    showHistory = true
                                                } else {
                                                    showThemeSelector = true
                                                }
                                            }
                                        }
                                        lastSwipeTime = System.currentTimeMillis()
                                    }
                                    accumulatedDrag = Offset.Zero
                                },
                                onDrag = { change, dragAmount ->
                                    accumulatedDrag += dragAmount
                                    change.consume()
                                }
                            )
                        }


                ) {
                    CalculatorDisplay(
                        state = state,
                        modifier = Modifier.fillMaxWidth()
                    )

                    CalculatorButtonsGrid(
                        onNumberClick = { number ->
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Number(number))
                        },
                        onOperationClick = { operation ->
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Operation(operation))
                        },
                        onEqualsClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Calculate)
                        },
                        onClearClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Clear)
                        },
                        onBackspaceClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Backspace)
                        },
                        onToggleSignClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.ToggleSign)
                        },
                        onDecimalClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Decimal)
                        },
                        onPercentClick = {
                            vibrator?.vibrate(
                                VibrationEffect.createOneShot(
                                    50,
                                    VibrationEffect.DEFAULT_AMPLITUDE
                                )
                            )
                            viewModel.onAction(CalculatorAction.Percent)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    if (showHistory) {
                        ModalBottomSheet(onDismissRequest = { showHistory = false }) {
                            CalculationHistoryGrid(
                                calculations,
                                viewModel::onCalculationSelected
                            )
                        }
                    }

                    if (showThemeSelector) {
                        BasicAlertDialog(
                            onDismissRequest = { showThemeSelector = false },
                            content = {
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    if (selectedTheme != null) {
                                        ThemeSelector(
                                            currentTheme = selectedTheme!!,
                                            onThemeSelected = { selected ->
                                                viewModel.onThemeSelected(selected)
                                                showThemeSelector = false
                                            },
                                        )
                                    } else {
                                        CircularProgressIndicator()
                                    }
                                }
                            }
                        )
                    }
                }
            }

        }
    }
    else {
        CircularProgressIndicator()
    }
}