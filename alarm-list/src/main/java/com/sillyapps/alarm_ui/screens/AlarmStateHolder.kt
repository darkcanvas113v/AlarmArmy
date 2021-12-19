package com.sillyapps.alarm_ui.screens

import androidx.compose.runtime.State
import com.sillyapps.alarm_ui.model.UIAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmStateHolder {

  fun getAlarms(): Flow<List<UIAlarm>>

  fun toggleAlarm(alarmId: Long)

}