package com.example.calculator.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calculator.presentation.ui.screen.auth.LoginScreen
import com.example.calculator.presentation.ui.screen.auth.RegistrationScreen
import com.example.calculator.presentation.ui.screen.auth.ResetPasswordScreen
import com.example.calculator.presentation.ui.screen.calculator.CalculatorScreen

@Composable
fun AppNavigation(startDestination: String) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        NavigationManager.navigationCommands.collect { destination ->
            navController.navigate(destination) {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") { LoginScreen() }
        composable("registration") { RegistrationScreen() }
        composable("reset-password") { ResetPasswordScreen() }
        composable("calculator") { CalculatorScreen() }
    }
}
