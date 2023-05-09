package com.alexmenaya.timeme.timer

import com.alexmenaya.timeme.timer.utilities.ElapsedTimeCalculator
import com.alexmenaya.timeme.timer.utilities.TimeStampFormatter

class StopWatchStateHolder(
    private val stopwatchStateCalculator: StopWatchStateCalculator,
    private val elapsedTimeCalculator: ElapsedTimeCalculator,
    private val timeStampFormatter: TimeStampFormatter
){

    var currentState: StopWatchState = StopWatchState.Paused(0)

    fun start() {
        currentState = stopwatchStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = stopwatchStateCalculator.calculatePausedState(currentState)
    }

    fun stop(){
        currentState = StopWatchState.Paused(0)
    }

    fun getStringTime(): String {
        val elapsedTime = when (val currentState = currentState) {
            is StopWatchState.Paused -> currentState.elapsedTime
            is StopWatchState.Running -> elapsedTimeCalculator.calculate((currentState))
        }
        return timeStampFormatter.format(elapsedTime)
    }
}