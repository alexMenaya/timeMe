package com.alexmenaya.timeme.timer

import android.util.Log

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

    fun setInitTime(initTime: Long) {
        currentState = TimerState.Paused(initTime)
        Log.e("VERY IMPORTANT", "I am setting the time")
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