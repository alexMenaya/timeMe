package com.alexmenaya.timeme.ui

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.alexmenaya.timeme.timer.StopWatchOrchestrator
import com.alexmenaya.timeme.timer.StopWatchStateCalculator
import com.alexmenaya.timeme.timer.TimeStampProvider
import com.alexmenaya.timeme.timer.utilities.ElapsedTimeCalculator
import com.alexmenaya.timeme.timer.utilities.TimeStampFormatter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Timer stuff
    private var timestampProvider = object : TimeStampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }
    private val elapsedTimeCalculator = ElapsedTimeCalculator(timestampProvider)
    private val stopwatchStateCalculator = StopWatchStateCalculator(
        timeStampProvider = timestampProvider,
        elapsedTimeCalculator = elapsedTimeCalculator
    )
    private val timestampMillisecondsFormatter = TimeStampFormatter()

    private fun resetTimer() {
        _uiState.update { currentState ->
            currentState.copy(
                activeTimerCount = 0
            )
        }
    }
}