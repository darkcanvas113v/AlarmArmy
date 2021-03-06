package com.sillyapps.alarmarmy.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.sillyapps.alarm_data.di.AlarmDbComponent
import com.sillyapps.alarm_editor_ui.api.AlarmEditorNavigation
import com.sillyapps.alarm_ui.api.MainScreenNavigation
import com.sillyapps.alarmarmy.ui.Screen

fun NavGraphBuilder.alarmGraph(
  navController: NavController,
  alarmDbComponent: AlarmDbComponent
) {
  navigation(startDestination = Screen.AlarmList.name, route = AlarmNavigation.alarmGraphRoute) {

    composable(
      route = Screen.AlarmList.name,

    ) {
      MainScreenNavigation(
        repository = alarmDbComponent.alarmRepository,
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
        alarmRepository = alarmDbComponent.alarmRepository,
        onConfirmationButtonClicked = { navController.navigateUp() }
      )
    }

  }
}

object AlarmNavigation {
  const val alarmGraphRoute = "alarmGraph"
}