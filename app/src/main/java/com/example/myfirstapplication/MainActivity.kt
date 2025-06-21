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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun AppNavigator(modifier: Modifier) {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "login", modifier = modifier.padding(20.dp)) {
        composable("home") { Home(navController) }
        composable("profile") { Profile(navController) }
        composable("login") { Login(navController) }
    }
}

@Composable
fun Login (navController: NavController) {
    var email by remember { mutableStateOf("") }
    var pass by remember { mutableStateOf("") }

    Column (
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login")
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.padding(24.dp))
        OutlinedTextField(
            value = pass,
            onValueChange = { pass = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = { navController.navigate("home") }
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