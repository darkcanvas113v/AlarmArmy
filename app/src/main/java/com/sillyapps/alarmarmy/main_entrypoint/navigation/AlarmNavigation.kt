package com.sillyapps.alarmarmy.main_entrypoint.navigation

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.sillyapps.alarm_data.di.AlarmDbComponent
import com.sillyapps.alarm_editor_ui.api.AlarmEditorNavigation
import com.sillyapps.alarm_ui.api.MainScreenNavigation
import com.sillyapps.alarmarmy.main_entrypoint.ui.Screen

fun NavGraphBuilder.alarmGraph(
  navController: NavController,
  alarmDbComponent: AlarmDbComponent
) {
  navigation(startDestination = Screen.AlarmList.name, route = AlarmNavigation.alarmGraphRoute) {

    composable(Screen.AlarmList.name) {
      MainScreenNavigation(
        repository = alarmDbComponent.repository,
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
        alarmRepository = alarmDbComponent.repository,
        onConfirmationButtonClicked = { navController.navigateUp() }
      )
    }

  }
}

object AlarmNavigation {
  const val alarmGraphRoute = "alarmGraph"
}