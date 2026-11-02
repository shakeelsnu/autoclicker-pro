package com.autoclicker.pro

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.autoclicker.pro.ui.theme.AutoClickerProTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutoClickerProTheme {
                // Navigation Compose Single Activity Architecture
            }
        }
    }
}