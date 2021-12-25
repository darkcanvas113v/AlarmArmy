package com.sillyapps.alarmarmy.navigation

import android.content.Context
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.sillyapps.alarm_data.di.AlarmComponent
import com.sillyapps.alarm_data.di.DaggerAlarmComponent
import com.sillyapps.alarm_editor_ui.ui.AlarmEditorNavigation
import com.sillyapps.alarm_ui.MainScreenNavigation
import com.sillyapps.alarmarmy.ui.Screen

fun NavGraphBuilder.alarmGraph(
  navController: NavController,
  context: Context,
  alarmComponent: AlarmComponent
) {
  navigation(startDestination = Screen.AlarmList.name, route = AlarmNavigation.alarmGraphRoute) {

    composable(Screen.AlarmList.name) {
      MainScreenNavigation(
        context = context,
        alarmRepository = alarmComponent.repository,
        onItemClick = { alarmId ->
          navController.navigate("${Screen.AlarmEditor.name}/$alarmId")
        })
    }

    val alarmScreenName = Screen.AlarmEditor.name
    composable(
      route = "${alarmScreenName}/{alarmId}",
      arguments = listOf(navArgument("alarmId") {
        type = NavType.LongType
      })) { entry ->

      AlarmEditorNavigation(
        alarmId = entry.arguments?.getLong("alarmId") ?: 0,
        context = context,
        alarmDao = alarmComponent.getAlarmDao(),
        onConfirmationButtonClicked = { navController.navigateUp() }
      )
    }

  }
}

object AlarmNavigation {
  const val alarmGraphRoute = "alarmGraph"
}