package com.example.myapplication.presentation.login

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel,
    navController: NavHostController
) {
    val userInput by viewModel.userInput.collectAsStateWithLifecycle()
    val mailInput by viewModel.mailInput.collectAsStateWithLifecycle()
    val isLoginPersisted by viewModel.isLoginPersisted.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = userInput,
            onValueChange = { viewModel.onUserInputChange(it) },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = mailInput,
            onValueChange = { viewModel.onMailInputChange(it) },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isLoginPersisted,
                onCheckedChange = { viewModel.onLoginPersistedChange(it) }
            )
            Text("remember me")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.performLogin() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (val state = uiState) {
            is LoginState.Loading -> {
                CircularProgressIndicator()
            }

            is LoginState.Error -> {
                Text(
                    text = state.message ?: " unknown error occurred",
                    color = MaterialTheme.colorScheme.error
                )
            }

            is LoginState.Success -> {
                navController.navigate("logged") {
                    popUpTo("login") { inclusive = true }
                }
            }

            is LoginState.Idle -> {
                Log.d("LoginScreen", "LoginState.idle")
            }
        }
    }
}
