package com.alexmenaya.timeme.ui

import androidx.lifecycle.ViewModel
import com.alexmenaya.timeme.singletons.DataController
import com.alexmenaya.timeme.timer.TimerOrchestrator
import com.alexmenaya.timeme.timer.TimerStateCalculator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val scope = CoroutineScope(Dispatchers.IO)
    private val orchestrator = TimerOrchestrator(scope)
    val timerValue: StateFlow<String> = orchestrator.ticker
    val isTimerActive: StateFlow<Boolean> = orchestrator.isTimerActive

    private var currentTaskName: String? = null
    fun setCurrentTaskName(name: String) {
        currentTaskName = name
    }

    fun startTimer() {
        orchestrator.start()
    }

    fun pauseTimer() {
        orchestrator.pause()
    }

    fun stopTimer() {
        orchestrator.stop()
    }

}