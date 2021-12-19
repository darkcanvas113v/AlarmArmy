package com.sillyapps.alarm_editor_ui.ui

import android.content.Context
import androidx.compose.runtime.Composable
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_editor_ui.di.DaggerAlarmEditorComponent
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorScreen
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorViewModel
import com.sillyapps.core_ui.daggerViewModel
import kotlinx.coroutines.CoroutineDispatcher

@Composable
fun AlarmEditorNavigation(
  alarmId: Long,
  context: Context,
  alarmDao: AlarmDao,
  onConfirmationButtonClicked: () -> Unit
) {

  val component = DaggerAlarmEditorComponent.builder()
    .context(context)
    .alarmID(alarmId)
    .alarmDao(alarmDao)
    .build()

  val viewModel: AlarmEditorViewModel = daggerViewModel {
    component.getViewModel()
  }

  AlarmEditorScreen(
    stateHolder = viewModel,
    onConfirmationButtonClicked = onConfirmationButtonClicked)
}