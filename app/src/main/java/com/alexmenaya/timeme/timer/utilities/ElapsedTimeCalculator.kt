package com.alexmenaya.timeme.timer.utilities

import com.alexmenaya.timeme.timer.StopWatchState
import com.alexmenaya.timeme.timer.TimeStampProvider

class ElapsedTimeCalculator(
    private val timeStampProvider: TimeStampProvider
) {
    fun calculate(
        state: StopWatchState.Running
    ): Long {
        val currentTimestamp = timeStampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime) {
            currentTimestamp - state.startTime
        } else {
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}