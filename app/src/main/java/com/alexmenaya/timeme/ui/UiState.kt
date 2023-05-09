package com.alexmenaya.timeme.ui

data class UiState(
    val isTimerActive: Boolean = false,
    val activeTimerStartTime: Long = 0L,
    val activeTimerCount: Int = 0
)