package com.alexmenaya.timeme.timer

object TimerStateCalculator {

    fun calculateRunningState(
        oldState: TimerState
    ): TimerState.Running = when(oldState) {
        is TimerState.Paused -> {
            TimerState.Running(
                startTime = System.currentTimeMillis(),
                elapsedTime = oldState.elapsedTime
            )
        }
        else -> {
            oldState as TimerState.Running
        }
    }

    fun calculatePausedState(
        oldState: TimerState
    ): TimerState.Paused = when(oldState) {
        is TimerState.Running -> {
            val elapsedTime = calculateElapsedTime(oldState)
            TimerState.Paused(elapsedTime = elapsedTime)
        }
        else -> {
            oldState as TimerState.Paused
        }
    }

    fun calculateElapsedTime(
        state: TimerState.Running
    ): Long {
        val currentTimeStamp = System.currentTimeMillis()
        val timePassedSinceStart = if (currentTimeStamp > state.startTime) {
            currentTimeStamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }

    fun format(
        timestamp: Long
    ): String {
        val seconds = timestamp / 1000
        val secondsFormatted = (seconds % 60).toString().padStart(2, '0')
        val minutes = seconds / 60
        val minutesFormatted = (minutes % 60).toString().padStart(2, '0')
        val hours = minutes / 60
        val hoursFormatted = hours.toString().padStart(2, '0')
        return "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    }

}