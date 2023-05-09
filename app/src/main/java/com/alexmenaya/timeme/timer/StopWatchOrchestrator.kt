package com.alexmenaya.timeme.timer

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class StopWatchOrchestrator(
    private val stopWatchStateHolder: StopWatchStateHolder,
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val _ticker = MutableStateFlow("")
    val ticker: StateFlow<String> = _ticker

    fun start() {
        if (job == null) startJob()
        stopWatchStateHolder.start()
    }

    fun stop() {
        stopWatchStateHolder.stop()
        stopJob()
        clearTicker()
    }

    fun pause() {
        stopWatchStateHolder.pause()
        stopJob()
    }

    private fun startJob() {
        scope.launch {
            while (isActive) {
                _ticker.value = stopWatchStateHolder.getStringTime()
                delay(20)
            }
        }
    }


    private fun stopJob() {
        scope.coroutineContext.cancelChildren()
        job = null
    }

    private fun clearTicker() {
        _ticker.value = ""
    }
}