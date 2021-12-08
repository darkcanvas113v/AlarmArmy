package com.sillyapps.alarmarmy.navigation

import android.content.Context
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.sillyapps.alarm_data.di.DaggerAlarmComponent
import com.sillyapps.alarm_editor_ui.di.DaggerAlarmEditorScreenComponent
import com.sillyapps.alarm_editor_ui.screen.AlarmEditorScreen
import com.sillyapps.alarm_editor_ui.screen.AlarmEditorViewModel
import com.sillyapps.alarm_ui.di.DaggerAlarmScreenComponent
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import com.sillyapps.alarm_ui.screens.AlarmScreen
import com.sillyapps.alarmarmy.ui.Screen
import com.sillyapps.core_ui.daggerViewModel

fun NavGraphBuilder.alarmGraph(
  navController: NavController,
  context: Context
) {
  navigation(startDestination = Screen.AlarmList.name, route = AlarmNavigation.alarmGraphRoute) {

    val alarmComponent = DaggerAlarmComponent.builder().context(context).build()

    composable(Screen.AlarmList.name) {
      val component = DaggerAlarmScreenComponent.builder()
        .context(context)
        .repository(alarmComponent.repository)
        .build()

      val viewModel: AlarmListViewModel = daggerViewModel {
        component.getViewModel()
      }

      AlarmScreen(
        stateHolder = viewModel,
        onAlarmClicked = { alarmId ->
            navController.navigate("${Screen.AlarmEditor.name}/$alarmId")
        }
      )

    }

    val alarmScreenName = Screen.AlarmEditor.name
    composable(
      route = "${alarmScreenName}/{alarmId}",
      arguments = listOf(navArgument("alarmId") {
        type = NavType.LongType
      })) { entry ->
      val alarmId = entry.arguments?.getLong("alarmId") ?: 0

      val component = DaggerAlarmEditorScreenComponent.builder()
        .context(context)
        .alarmID(alarmId)
        .repository(alarmComponent.repository)
        .build()

      val viewModel: AlarmEditorViewModel = daggerViewModel {
        component.getViewModel()
      }

      AlarmEditorScreen(
        alarmEditorStateHolder = viewModel) {
      }
    }

  }
}

object AlarmNavigation {
  const val alarmGraphRoute = "alarmGraph"
}