package com.example.calculator.presentation.ui.screen.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun ResetPassKeyScreen(navController: NavController, repository: PassKeyRepositoryImpl) {
    var newPassKey by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Введите новый Pass Key")
        TextField(value = newPassKey, onValueChange = { newPassKey = it })

        Button(onClick = {
            repository.encryptAndSave(newPassKey)
            navController.navigate("login") { popUpTo("resetPassKey") { inclusive = true } }
        }) {
            Text("Сохранить")
        }
    }
}
