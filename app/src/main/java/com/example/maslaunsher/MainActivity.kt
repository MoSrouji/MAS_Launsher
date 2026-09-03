package com.example.maslaunsher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.maslaunsher.presentation.launcher.LauncherScreen
import com.example.maslaunsher.ui.theme.MASLaunsherTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main entry point of the launcher.
 * 
 * We use [@AndroidEntryPoint] so Hilt can inject dependencies into this Activity
 * if needed, and to handle the lifecycle of our Composables correctly.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // enableEdgeToEdge makes our app draw behind the status bar and navigation bar,
        // which is a standard look for modern launchers.
        enableEdgeToEdge()
        
        setContent {
            MASLaunsherTheme {
                // LauncherScreen is our root UI component defined in the presentation layer.
                LauncherScreen()
            }
        }
    }
}
