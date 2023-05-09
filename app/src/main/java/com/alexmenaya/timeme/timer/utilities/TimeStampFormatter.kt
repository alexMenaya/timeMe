package com.alexmenaya.timeme.timer.utilities

class TimeStampFormatter {

    companion object {
        const val DEFAULT_TIME = "00:00:00"
    }

    fun format(timestamp: Long): String {
        val seconds = timestamp/1000
        val secondsFormatted = (seconds %60).toString().padStart(2, '0')
        val minutes = seconds/60
        val minutesFormatted = (minutes %60).toString().padStart(2, '0')
        val hours = minutes/60
        val hoursFormatted = (hours %60).toString()
        return "$hoursFormatted:$minutesFormatted:$secondsFormatted"
    }
}