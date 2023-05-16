package com.alexmenaya.timeme.timer

object TimerStateHolder {

    var currentState: TimerState = TimerState.Paused(0)

    fun start() {
        currentState = TimerStateCalculator.calculateRunningState(currentState)
    }

    fun pause() {
        currentState = TimerStateCalculator.calculatePausedState(currentState)
    }

    fun stop() {
        currentState = TimerState.Paused(0)
    }

    fun getStringTime(): String {
        val elapsedTime = when(val currentState = currentState) {
            is TimerState.Running -> TimerStateCalculator.calculateElapsedTime(currentState)
            is TimerState.Paused -> currentState.elapsedTime
            else -> 0
        }
        return TimerStateCalculator.format(elapsedTime)
    }

}