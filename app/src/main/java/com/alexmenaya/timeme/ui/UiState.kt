package com.alexmenaya.timeme.ui

import com.alexmenaya.timeme.data.Task

data class UiState(
    // Timer Screen
    var listOfTasks: List<Task> = listOf(), // This will be future selectable tasks
    var currentTaskActive: Task? = null
)