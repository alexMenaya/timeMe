package com.alexmenaya.timeme.ui.composables

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alexmenaya.timeme.R
import com.alexmenaya.timeme.ui.composables.screens.TimerScreen

enum class TimeMeScreen(
    @StringRes val title: Int
) {
    TIMER(title = R.string.timer_screen_title)
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

    Scaffold() { innerPaddingValuer ->
        NavHost(
            navController = navController,
            startDestination = TimeMeScreen.TIMER.name,
            modifier = modifier.padding(innerPaddingValuer)
        ) {
            composable(route = TimeMeScreen.TIMER.name) {
                TimerScreen()
            }
        }
    }
}