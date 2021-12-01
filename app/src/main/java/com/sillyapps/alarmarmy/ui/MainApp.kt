package com.sillyapps.alarmarmy.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sillyapps.alarm_ui.di.DaggerAlarmComponent
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import com.sillyapps.alarm_ui.screens.AlarmScreen
import com.sillyapps.alarmarmy.di.DaggerAppComponent
import com.sillyapps.core_ui.daggerViewModel
import com.sillyapps.core_ui.theme.AlarmArmyTheme

@Composable
fun MainApp() {
  val appComponent = DaggerAppComponent.builder().build()
  AlarmArmyTheme {
    val allScreens = Screen.values().toList()
    val navController = rememberNavController()
    val backStackEntry = navController.currentBackStackEntryAsState()
    val currentScreen = Screen.fromRoute(backStackEntry.value?.destination?.route)

    NavHost(
      navController = navController,
      startDestination = Screen.Main.name
    ) {
      composable(Screen.Main.name) {
        val component = DaggerAlarmComponent.builder().deps(appComponent).build()

        val viewModel: AlarmListViewModel = daggerViewModel {
          component.getViewModel()
        }

        AlarmScreen(stateHolder = viewModel)

      }

      val alarmScreenName = Screen.Alarm.name
      composable(
        route = "${alarmScreenName}/{id}",
        arguments = listOf(
          navArgument("alarmId") {
            type = NavType.LongType
          }
        )
      ) {

      }

      composable(Screen.Profile.name) {

      }
    }
  }
}