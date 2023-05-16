package com.alexmenaya.timeme.timer

open class TimerState {

    data class Paused(
        val elapsedTime: Long
    ): TimerState()

    data class Running(
        val startTime: Long,
        val elapsedTime: Long
    ): TimerState()

}