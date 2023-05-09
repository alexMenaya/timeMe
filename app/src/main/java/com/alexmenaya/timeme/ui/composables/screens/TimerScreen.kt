package com.alexmenaya.timeme.ui.composables.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import com.alexmenaya.timeme.ui.theme.TimeMeTheme
import kotlinx.coroutines.delay


@Composable
fun TimerScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Timer()
    }
}

@Composable
fun Timer() {
    var sec by remember { mutableStateOf(0) }
    val secSt by remember(sec) { mutableStateOf("${sec/1000}") }
    LaunchedEffect(key1 = Unit, block = {
        while (true) {
            delay(1000)
            sec += 1000
        }
    })
    Text(
        text = secSt,
        fontSize = 48.sp,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    TimeMeTheme {
        TimerScreen()
    }
}