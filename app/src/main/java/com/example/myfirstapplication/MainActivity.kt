package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    AppNavigator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigator(modifier: Modifier) {

    var snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()

    Scaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) {
        padding -> NavHost(navController, startDestination = "login", modifier = modifier.padding(padding)) {
            composable("home") { Home(navController) }
            composable("profile") { Profile(navController) }
            composable("login") { Login(navController, snackbarHostState, scope) }
        }
    }
}

@Composable
fun Login (navController: NavController, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {

    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    val isEmailValid = email.contains("@") && email.isNotEmpty()
    val isPassValid = pass.length > 6
    val isFormValid = isPassValid && isEmailValid
        Column (
            modifier = Modifier.padding(20.dp).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Login")
            Spacer(modifier = Modifier.padding(24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                isError = !isEmailValid && email.isNotEmpty(),
                label = { Text("E-mail") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.padding(24.dp))
            OutlinedTextField(
                value = pass,
                onValueChange = { pass = it },
                label = { Text("Password") },
                isError = !isPassValid && pass.isNotEmpty(),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    //old way
                    //CoroutineScope(Dispatchers.Main).launch {snackbarHostState.showSnackbar("login successful")}

                    //new way
                    //put scope in a place where it wont be removed on page change
                    scope.launch {
                        snackbarHostState.showSnackbar("Login successful")
                        navController.navigate("home") {
                            //prevent user from going back to login screen
                            popUpTo("login") {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
                enabled = isFormValid
            ) {
                Text(text = "Login")
            }
        }
}

@Composable
fun Home(navController: NavController) {
    fun handleClick () {
        navController.navigate("profile")
    }

    Column {
        Text(text = "Welcome to home")
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = { handleClick() }) {
            Text(text = "go to profile")
        }
    }
}

@Composable
fun Profile(navController: NavController) {
    fun handleClick () {
        navController.popBackStack()
    }

    Column {
        Text(text = "Welcome to profile")
        Spacer(modifier = Modifier.padding(20.dp))
        Button(onClick = { handleClick() }) {
            Text(text = "go to home")
        }
    }
}