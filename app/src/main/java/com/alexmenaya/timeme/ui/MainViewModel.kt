package com.alexmenaya.timeme.ui

import androidx.lifecycle.ViewModel
import com.alexmenaya.timeme.timer.TimerStateCalculator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    val s = TimerStateCalculator()

}