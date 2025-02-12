package com.example.calculator.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.calculator.presentation.ui.screen.calculator.CalculatorScreen


@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        val isAuthenticated = false
        if (isAuthenticated) {
            navController.navigate("calculator") { popUpTo("login") { inclusive = true } }
        }
    }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, passKeyRepository) }
        composable("register") { RegisterScreen(navController, passKeyRepository) }
        composable("calculator") { CalculatorScreen() }
    }
}
