package com.sillyapps.alarmarmy.navigation

import android.content.Context
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.sillyapps.alarm_data.di.AlarmComponent
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_editor_ui.ui.AlarmEditorNavigation
import com.sillyapps.alarm_ui.api.MainScreenNavigation
import com.sillyapps.alarmarmy.ui.Screen

fun NavGraphBuilder.alarmGraph(
  navController: NavController,
  context: Context,
  alarmComponent: AlarmComponent
) {
  navigation(startDestination = Screen.AlarmList.name, route = AlarmNavigation.alarmGraphRoute) {

    composable(Screen.AlarmList.name) {
      MainScreenNavigation(
        repository = alarmComponent.repository,
        onItemClick = { id -> navController.navigate("${Screen.AlarmEditor.name}/$id") })
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