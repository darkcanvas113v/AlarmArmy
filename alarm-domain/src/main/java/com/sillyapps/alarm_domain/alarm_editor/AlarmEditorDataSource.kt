package com.sillyapps.alarm_domain.alarm_editor

import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmEditorDataSource {
  fun update(alarm: Alarm)

  fun getAlarm(): Flow<Alarm>
}