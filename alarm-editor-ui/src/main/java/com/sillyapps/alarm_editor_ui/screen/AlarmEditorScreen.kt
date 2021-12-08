package com.sillyapps.alarm_editor_ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sillyapps.alarm_editor_ui.components.TimePicker

@Composable
fun AlarmEditorScreen(
  alarmEditorStateHolder: AlarmEditorStateHolder,
  onOkButtonClick: () -> Unit
){
  val alarm = alarmEditorStateHolder.alarm
  Surface(modifier = Modifier.fillMaxSize()) {
    Box {
      Column(Modifier.fillMaxSize()) {
        TimePicker(
          hours = alarm.timeHours,
          minutes = alarm.timeMinutes,
          onHoursChanged = { alarm.timeHours = it },
          onMinutesChanged = { alarm.timeMinutes = it } )
      }
    }

  }

}