package com.sillyapps.alarm_ui

import android.content.Context
import androidx.compose.runtime.Composable
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_ui.di.DaggerAlarmScreenComponent
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import com.sillyapps.alarm_ui.screens.AlarmScreen
import com.sillyapps.core_ui.daggerViewModel

@Composable
fun MainScreenNavigation(
  context: Context,
  alarmRepository: AlarmRepository,
  onItemClick: (Long) -> Unit
) {
  val component = DaggerAlarmScreenComponent.builder()
    .context(context)
    .repository(alarmRepository)
    .build()

  val viewModel: AlarmListViewModel = daggerViewModel {
    component.getViewModel()
  }

  AlarmScreen(
    stateHolder = viewModel,
    onAlarmClicked = onItemClick
  )
}