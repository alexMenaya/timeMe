package com.alexmenaya.timeme.ui.composables.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexmenaya.timeme.ui.theme.TimeMeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
fun TimerScreen(
    timerValue: String,
    isTimerActive: Boolean,
    startTimer: () -> Unit,
    stopTimer: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isTimerActive) {
            TimerDisplay(
                timerValue = timerValue,
                stopTimer = stopTimer
            )
        } else {
            InputField(
                startTimer = startTimer
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Composable
fun TimerDisplay(
    timerValue: String,
    stopTimer: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = timerValue,
            fontSize = 48.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = stopTimer
        ) {
            Icon(
                imageVector = Icons.Filled.Stop,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
fun InputField(
    startTimer: () -> Unit
){
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        var content by remember { mutableStateOf(TextFieldValue("")) }
        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            label = { Text(text = "Next task!") },
            supportingText = { Text(text = "Here support text") },
            modifier = Modifier.weight(1f)
        )
        IconButton(
            onClick = { if (content.text.isNotBlank()) startTimer() }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimerPreview() {
    TimeMeTheme {
        TimerScreen("00:00:00", true,  {}, {})
    }
}