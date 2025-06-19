package com.example.myfirstapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myfirstapplication.ui.theme.MyFirstApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFirstApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var clicks by remember { mutableStateOf(0) }
    var color by remember { mutableStateOf(androidx.compose.ui.graphics.Color.Red) }

    fun handleClick () {
        clicks += 1
        var test = clicks % 10
        if (test < 5) {
            color = androidx.compose.ui.graphics.Color.Red
        } else {
            color = androidx.compose.ui.graphics.Color.Green
        }
    }

    fun handleReset () {
        clicks = 0
    }

    Column (
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Text(
            text = "Button clicked $clicks times",
            modifier = modifier
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Button(
            onClick = { handleClick() },
            modifier = Modifier.background(color = color)
        ) {
            Text(text = "Click me")
        }
        if ( clicks > 0 ) {
            Spacer(modifier = Modifier.padding(20.dp))
            Button(
                onClick = { handleReset() }
            ) {
                Text(text = "Reset")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyFirstApplicationTheme {
        Greeting()
    }
}