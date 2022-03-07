package com.sillyapps.alarmarmy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sillyapps.alarm_data.di.AlarmDbComponent
import com.sillyapps.alarmarmy.di.AppComponent
import com.sillyapps.alarmarmy.ui.Screen
import com.sillyapps.alarmarmy.ui.TabItem
import com.sillyapps.alarmarmy.ui.Tabs
import com.sillyapps.profiler_db.di.ProfilerDbComponent

@Composable
fun AppNavHost(
  appComponent: AppComponent,
  alarmDbComponent: AlarmDbComponent,
  profilerDbComponent: ProfilerDbComponent
) {
  val allScreens = Screen.values().toList()
  val navController = rememberNavController()
  val backStackEntry = navController.currentBackStackEntryAsState()
  val currentScreen = Screen.fromRoute(backStackEntry.value?.destination?.route)

  // TODO Fix backButton navigation
  Column() {
    Tabs(
      tabs = listOf(TabItem.Alarm, TabItem.Profiler),
      navController = navController
    )

    NavHost(
      navController = navController,
      startDestination = AlarmNavigation.alarmGraphRoute
    ) {

      alarmGraph(navController, alarmDbComponent)

      profilerGraph(navController, profilerDbComponent)
    }
  }
}