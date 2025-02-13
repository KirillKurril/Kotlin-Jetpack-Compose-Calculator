package com.example.calculator.presentation.ui.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.calculator.R
import com.example.calculator.presentation.viewmodel.RegistrationViewModel

@Composable
fun RegistrationScreen(
    viewModel: RegistrationViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {}
) {
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var pin by remember { mutableStateOf("") }
    var confirmPin by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var pinVisible by remember { mutableStateOf(false) }
    var useBiometrics by remember { mutableStateOf(false) }

    val passwordMatchError by viewModel.isPasswordMatch
    val pinMatchError by viewModel.isPinMatch
    val registrationError by viewModel.registrationError
    val isBiometricAvailable by viewModel.isBiometricAvailable
    val navigateTo by viewModel.navigateTo

    // Navigation handling
    LaunchedEffect(navigateTo) {
        navigateTo?.let { destination ->
            when (destination) {
                "login" -> onLoginClick()
            }
        }
    }

    LaunchedEffect(password, confirmPassword) {
        viewModel.checkPasswordMatch(password, confirmPassword)
    }

    LaunchedEffect(pin, confirmPin) {
        viewModel.checkPinMatch(pin, confirmPin)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Registration",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { 
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock), 
                        contentDescription = "Password" 
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            ),
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                isError = !passwordMatchError && confirmPassword.isNotEmpty()
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { 
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock), 
                        contentDescription = "Confirm Password" 
                    )
                },
                isError = !passwordMatchError && confirmPassword.isNotEmpty()
            )

            if (!passwordMatchError && confirmPassword.isNotEmpty()) {
                Text(
                    text = "Passwords do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = pin,
                onValueChange = { pin = it },
                label = { Text("Recovery PIN") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = if (pinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { 
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock), 
                        contentDescription = "Recovery PIN" 
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { pinVisible = !pinVisible }) {
                        Icon(
                            painter = painterResource(
                                id = if (pinVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                            ),
                            contentDescription = if (pinVisible) "Hide PIN" else "Show PIN"
                        )
                    }
                },
                isError = !pinMatchError && confirmPin.isNotEmpty()
            )

            OutlinedTextField(
                value = confirmPin,
                onValueChange = { confirmPin = it },
                label = { Text("Confirm Recovery PIN") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = if (pinVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = { 
                    Icon(
                        painter = painterResource(id = R.drawable.ic_lock), 
                        contentDescription = "Confirm Recovery PIN" 
                    )
                },
                isError = !pinMatchError && confirmPin.isNotEmpty()
            )

            if (!pinMatchError && confirmPin.isNotEmpty()) {
                Text(
                    text = "PIN codes do not match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            if (isBiometricAvailable) {
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = useBiometrics,
                        onCheckedChange = { 
                            useBiometrics = it
                            if (it) viewModel.onSetBiometricsPermissionGranted()
                        }
                    )
                    Text("Enable Biometric Authentication")
                }
            }

            registrationError?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { 
                    viewModel.onRegister(
                        password, 
                        confirmPassword, 
                        pin, 
                        confirmPin, 
                        useBiometrics
                    ) 
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = password.isNotEmpty() && 
                          confirmPassword.isNotEmpty() && 
                          pin.isNotEmpty() && 
                          confirmPin.isNotEmpty() &&
                          passwordMatchError && 
                          pinMatchError
            ) {
                Text("Register")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { viewModel.onBack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back to Login")
            }
        }
    }
}
