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

    fun setInitTime(initTime: Long) {
        TimerStateHolder.setInitTime(initTime)
    }
    fun start() {
        if (job == null) startJob()
        TimerStateHolder.start()
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
        stopJob()
    }

    fun stop() {
        TimerStateHolder.stop()
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