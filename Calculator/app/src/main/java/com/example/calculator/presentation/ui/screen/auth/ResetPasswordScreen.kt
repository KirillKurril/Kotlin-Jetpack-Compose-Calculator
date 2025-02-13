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
import com.example.calculator.presentation.viewmodel.ResetPasswordViewModel

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {}
) {
    var pin by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var currentStage by remember { mutableStateOf(ResetStage.PIN_VERIFICATION) }

    val isBiometricAllowed by viewModel.isBiometricAllow
    val accessConfirmed by viewModel.accessConfirmed
    val resetError by viewModel.resetError
    val navigateTo by viewModel.navigateTo

    LaunchedEffect(navigateTo) {
        navigateTo?.let { destination ->
            when (destination) {
                "login" -> onLoginClick()
                "calculator" -> {} // Add navigation to calculator if needed
            }
        }
    }

    LaunchedEffect(accessConfirmed) {
        if (accessConfirmed && currentStage == ResetStage.PIN_VERIFICATION) {
            currentStage = ResetStage.NEW_PASSWORD
        }
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
                text = "Reset Password",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            when (currentStage) {
                ResetStage.PIN_VERIFICATION -> {
                    OutlinedTextField(
                        value = pin,
                        onValueChange = { pin = it },
                        label = { Text("Recovery PIN") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        visualTransformation = PasswordVisualTransformation(),
                        leadingIcon = { 
                            Icon(
                                painter = painterResource(id = R.drawable.ic_lock), 
                                contentDescription = "Recovery PIN" 
                            )
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.onPinVerification(pin) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = pin.isNotEmpty()
                    ) {
                        Text("Verify PIN")
                    }

                    if (isBiometricAllowed) {
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        OutlinedButton(
                            onClick = { viewModel.onFingerprintCheck() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_fingerprint), 
                                contentDescription = "Biometric Login"
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Verify with Biometrics")
                        }
                    }
                }

                ResetStage.NEW_PASSWORD -> {
                    OutlinedTextField(
                        value = newPassword,
                        onValueChange = { newPassword = it },
                        label = { Text("New Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = { 
                            Icon(
                                painter = painterResource(id = R.drawable.ic_lock), 
                                contentDescription = "New Password" 
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
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmNewPassword,
                        onValueChange = { confirmNewPassword = it },
                        label = { Text("Confirm New Password") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        leadingIcon = { 
                            Icon(
                                painter = painterResource(id = R.drawable.ic_lock), 
                                contentDescription = "Confirm New Password" 
                            )
                        }
                    )

                    if (newPassword.isNotEmpty() && confirmNewPassword.isNotEmpty() &&
                        !viewModel.checkPasswordMatch(newPassword, confirmNewPassword)) {
                        Text(
                            text = "Passwords do not match",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { viewModel.onSetNewPassword(newPassword) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = newPassword.isNotEmpty() && 
                                  confirmNewPassword.isNotEmpty() && 
                                  viewModel.checkPasswordMatch(newPassword, confirmNewPassword)
                    ) {
                        Text("Reset Password")
                    }
                }
            }

            resetError?.let { error ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
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

enum class ResetStage {
    PIN_VERIFICATION,
    NEW_PASSWORD
}
