package ru.igorshaposhnikov.currents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import ru.igorshaposhnikov.currents.ui.screens.NewsListScreen
import ru.igorshaposhnikov.currents.ui.theme.CurrentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CurrentsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NewsListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}