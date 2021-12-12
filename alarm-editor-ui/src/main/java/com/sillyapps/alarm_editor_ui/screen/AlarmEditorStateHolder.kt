package com.sillyapps.alarm_editor_ui.screen

import com.sillyapps.alarm_editor_ui.model.UIAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmEditorStateHolder {
  val alarm: Flow<UIAlarm>

  fun enableAllDays()

  fun disableAllDays()

  fun toggleDay(day: Int)

  fun save()

  fun hoursChanged(hours: Int)

  fun minutesChanged(minutes: Int)
}