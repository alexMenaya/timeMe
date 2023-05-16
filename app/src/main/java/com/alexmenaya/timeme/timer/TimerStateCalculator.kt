package com.alexmenaya.timeme.timer

class TimerStateCalculator {

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

    private fun calculateElapsedTime(
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

}