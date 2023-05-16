package com.alexmenaya.timeme.ui.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexmenaya.timeme.ui.theme.TimeMeTheme
import kotlinx.coroutines.delay
import java.util.Timer


@Composable
fun TimerScreen() {
    var timerIsActive by remember { mutableStateOf(false) }
    var timerTime by remember { mutableStateOf(0) }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(onClick = { timerIsActive = true }) {
            Text(text = "Start")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { timerIsActive = false }) {
            Text(text = "Stop")
        }
    }

}
@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    TimeMeTheme {
        TimerScreen()
    }
}