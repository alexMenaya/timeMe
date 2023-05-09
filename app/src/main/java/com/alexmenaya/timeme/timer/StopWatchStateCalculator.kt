package com.alexmenaya.timeme.timer

import com.alexmenaya.timeme.timer.utilities.ElapsedTimeCalculator

class StopWatchStateCalculator(
    private val timeStampProvider: TimeStampProvider,
    private val elapsedTimeCalculator: ElapsedTimeCalculator
) {
    fun calculateRunningState(
        oldState: StopWatchState
    ): StopWatchState.Running =
        when (oldState) {
            is StopWatchState.Running -> oldState
            is StopWatchState.Paused -> {
                StopWatchState.Running(
                    startTime = timeStampProvider.getMilliseconds(),
                    elapsedTime = oldState.elapsedTime
                )
            }
        }

    fun calculatePausedState(
        oldState: StopWatchState
    ): StopWatchState.Paused =
        when (oldState) {
            is StopWatchState.Running -> {
                val elapsedTime = elapsedTimeCalculator.calculate(oldState)
                StopWatchState.Paused(elapsedTime = elapsedTime)
            }
            is StopWatchState.Paused -> oldState
        }
}