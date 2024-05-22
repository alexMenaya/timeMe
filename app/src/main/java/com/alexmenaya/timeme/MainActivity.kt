package com.alexmenaya.timeme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.alexmenaya.timeme.singletons.AppController
import com.alexmenaya.timeme.singletons.DataController
import com.alexmenaya.timeme.ui.composables.TimeMeApp
import com.alexmenaya.timeme.ui.theme.TimeMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Starting the timer
        // Other updates
        // More updates
        DataController.bind(this)
        AppController.bind(this)
    }

    override fun onStart() {
        setContent {
            TimeMeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TimeMeApp()
                }
            }
        }
        super.onStart()
    }
}