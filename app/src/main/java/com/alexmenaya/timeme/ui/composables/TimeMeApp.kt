package com.alexmenaya.timeme.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexmenaya.timeme.R
import com.alexmenaya.timeme.ui.MainViewModel
import com.alexmenaya.timeme.ui.composables.screens.TimerScreen

enum class TimeMeScreen(
    @StringRes val title: Int
) {
    TIMER(title = R.string.timer_screen_title),
    PROJECTS(title = R.string.timer_screen_title)
}

@Composable
fun TimeMeApp(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = TimeMeScreen.valueOf(
        backStackEntry?.destination?.route ?: TimeMeScreen.TIMER.name
    )
    val uiState by viewModel.uiState.collectAsState()
    val timerValue by viewModel.timerValue.collectAsState()


    Scaffold(
        topBar = {
            NavigationBar()
        }
    ) { innerPaddingValuer ->
        NavHost(
            navController = navController,
            startDestination = TimeMeScreen.TIMER.name,
            modifier = modifier.padding(innerPaddingValuer)
        ) {
            composable(route = TimeMeScreen.TIMER.name) {
                TimerScreen(
                    timerValue = timerValue,
                    activeTask = uiState.currentTaskActive,
                    startTimer = { viewModel.startTimer() },
                    stopTimer = { viewModel.stopTimer() },
                    setTaskName = { viewModel.setCurrentTaskName(it) },
                    listOfTasks = uiState.listOfTasks,
                    testing = {viewModel.testing()}
                )
            }
        }
    }
}

@Composable
fun NavigationBar(
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { NavigationList() }
    )
}
@Composable
fun NavigationList() {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp),
        contentPadding = PaddingValues(4.dp)
    ) {
        item {
            Icon(
                imageVector = Icons.Filled.Timer, // Timer
                contentDescription = null
            )
        }
        item {
            Icon(
                imageVector = Icons.Filled.Folder, //Projects
                contentDescription = null
            )
        }
        item {
            Icon(
                imageVector = Icons.Filled.CalendarMonth, // Tracking
                contentDescription = null
            )
        }
        item {
            Icon(
                imageVector = Icons.Filled.BarChart, //Stats
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun MainPreview() {
    TimeMeApp()
}