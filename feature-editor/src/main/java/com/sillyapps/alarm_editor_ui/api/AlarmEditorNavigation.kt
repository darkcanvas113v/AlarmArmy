package com.sillyapps.alarm_editor_ui.api

import androidx.compose.runtime.Composable
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_editor_ui.di.DaggerAlarmEditorComponent
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorScreen
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorViewModel
import com.sillyapps.core_ui.daggerViewModel

@Composable
fun AlarmEditorNavigation(
  alarmId: Long,
  alarmRepository: AlarmRepository,
  onConfirmationButtonClicked: () -> Unit
) {

  val component = DaggerAlarmEditorComponent.builder()
    .alarmID(alarmId)
    .repository(alarmRepository)
    .build()

  val viewModel: AlarmEditorViewModel = daggerViewModel {
    component.getViewModel()
  }

  AlarmEditorScreen(
    stateHolder = viewModel,
    onConfirmationButtonClicked = onConfirmationButtonClicked)
}