package com.sillyapps.alarm_ui.ui

import com.sillyapps.alarm_ui.model.UIAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmStateHolder {

  fun getAlarms(): Flow<List<UIAlarm>>

  fun toggleAlarm(alarmId: Long)

}