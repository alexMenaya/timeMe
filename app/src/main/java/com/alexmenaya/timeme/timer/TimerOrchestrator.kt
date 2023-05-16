package com.alexmenaya.timeme.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerOrchestrator(
    private val scope: CoroutineScope
) {

    private var job: Job? = null
    private val defaultTicker = "00:00:00"
    private val _ticker = MutableStateFlow(defaultTicker)
    val ticker: StateFlow<String> = _ticker
    private val _isTimerActive = MutableStateFlow(false)
    val isTimerActive: StateFlow<Boolean> = _isTimerActive

    fun start() {
        if (job == null) startJob()
        TimerStateHolder.start()
        _isTimerActive.value = true
    }

    private fun startJob() {
        scope.launch {
            while (isActive) {
                _ticker.value = TimerStateHolder.getStringTime()
                delay(100)
            }
        }
    }

    fun pause() {
        TimerStateHolder.pause()
        _isTimerActive.value = false
        stopJob()
    }

    fun stop() {
        TimerStateHolder.stop()
        _isTimerActive.value = false
        stopJob()
        clearValue()
    }

    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearValue() {
        _ticker.value = defaultTicker
    }

}