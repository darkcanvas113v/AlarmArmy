package com.sillyapps.alarmarmy.main_entrypoint.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.sillyapps.alarm_data.di.AlarmDbComponent
import com.sillyapps.alarmarmy.di.AppComponent
import com.sillyapps.alarmarmy.main_entrypoint.ui.Screen

@Composable
fun AppNavHost(
  appComponent: AppComponent,
  alarmDbComponent: AlarmDbComponent
) {
  val allScreens = Screen.values().toList()
  val navController = rememberNavController()
  val backStackEntry = navController.currentBackStackEntryAsState()
  val currentScreen = Screen.fromRoute(backStackEntry.value?.destination?.route)

  NavHost(
    navController = navController,
    startDestination = AlarmNavigation.alarmGraphRoute
  ) {

    alarmGraph(navController, alarmDbComponent)

    composable(Screen.Profile.name) {

    }
  }
}