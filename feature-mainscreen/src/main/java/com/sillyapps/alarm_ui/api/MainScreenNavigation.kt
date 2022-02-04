package com.sillyapps.alarm_ui.api

import androidx.compose.runtime.Composable
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_ui.di.DaggerMainScreenComponent
import com.sillyapps.alarm_ui.ui.AlarmListViewModel
import com.sillyapps.alarm_ui.ui.AlarmScreen
import com.sillyapps.core_ui.daggerViewModel

@Composable
fun MainScreenNavigation(
  repository: AlarmRepository,
  onItemClick: (Long) -> Unit
) {
  val component = DaggerMainScreenComponent.builder()
    .alarmRepository(repository)
    .build()

  val viewModel: AlarmListViewModel = daggerViewModel {
    component.getViewModel()
  }

  AlarmScreen(
    stateHolder = viewModel,
    onAlarmClicked = onItemClick
  )
}