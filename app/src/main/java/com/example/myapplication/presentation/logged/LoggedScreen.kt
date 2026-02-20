package com.example.myapplication.presentation.logged

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController

@Composable
fun LoggedScreen(
    modifier: Modifier = Modifier,
    viewModel: LoggedViewModel,
    navController: NavHostController
) {
    val loggedUser by viewModel.loggedUser.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loggedUser != null) {
            Text(text = "User logged", style = MaterialTheme.typography.headlineMedium)
            
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Username: ${loggedUser?.userName}")
            Text(text = "Email: ${loggedUser?.email}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    viewModel.onLogoffClicked()

                    navController.navigate("login") {
                        popUpTo("logged") { inclusive = true }
                    }

                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Logoff")
            }
        } else {
            CircularProgressIndicator()
        }
    }
}
