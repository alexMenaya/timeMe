package com.alexmenaya.timeme.ui

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.alexmenaya.timeme.data.Task
import com.alexmenaya.timeme.singletons.DataController
import com.alexmenaya.timeme.timer.TimerOrchestrator
import com.alexmenaya.timeme.timer.TimerStateCalculator
import com.alexmenaya.timeme.timer.TimerStateHolder.currentState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.UUID

class MainViewModel: ViewModel() {
    // TODO: List of tasks should come ordered by timeStarted desc
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    // Things for the timer, this probably should go to their own viewModel
    private val scope = CoroutineScope(Dispatchers.IO)
    private val orchestrator = TimerOrchestrator(scope)
    val timerValue: StateFlow<String> = orchestrator.ticker

    init {
        updateList()
        lookForActiveTask()
    }

    private fun lookForActiveTask() {
        val activeTask = DataController.appDatabase.taskDao().selectOpenTask()
        activeTask?.let {
            val initTime = (System.currentTimeMillis()-it.time_started*1000)
            Log.e("VERY IMPORTANT", "$initTime, ${System.currentTimeMillis()/1000}, ${it.time_started}")
            orchestrator.setInitTime(initTime)
            orchestrator.start()
            updateList()
            updateActiveTask(activeTask)
        }

    }

    private var currentTaskName: String? = null
    fun setCurrentTaskName(name: String) {
        currentTaskName = name
    }

    private var temporaryUID: String? = null
    fun startTimer() {
        orchestrator.start()
        createTask()
        updateList()
    }

    private fun createTask(){
        val randomUID = UUID.randomUUID().toString()
        temporaryUID = randomUID
        val creationTime = System.currentTimeMillis()/1000
        val newTask = Task(
            temporaryUID!!,
            creationTime,
            creationTime,
            false,
            "Alex",
            currentTaskName!!,
            "New",
            creationTime,
            0
        )
        DataController.appDatabase.taskDao().insertAll(newTask)
        updateActiveTask(newTask)
    }

    fun pauseTimer() {
        orchestrator.pause()
    }

    fun stopTimer() {
        orchestrator.stop()
        closeTask()
        updateList()
    }

    private fun closeTask() {
        val closingTime = System.currentTimeMillis()/1000
        // TODO: here I am suposed to put the task that is selected
        val taskToClose = uiState.value.currentTaskActive
        taskToClose!!.time_ended = closingTime
        DataController.appDatabase.taskDao().update(taskToClose)
        updateActiveTask(null)
    }

    private fun updateList() {
        val newTasks = DataController.appDatabase.taskDao().getAll()
        _uiState.update { currentState ->
            currentState.copy(
                listOfTasks = newTasks
            )
        }
    }

    private fun updateActiveTask(task: Task?) {
        _uiState.update { currentState ->
            currentState.copy(
                currentTaskActive = task
            )
        }
    }

    fun testing() {

        val s = uiState.value
        print("hehehe")

        for (task in uiState.value.listOfTasks) {
            DataController.appDatabase.taskDao().delete(task)
        }
        updateList()
    }
}