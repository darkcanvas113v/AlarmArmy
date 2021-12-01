package com.sillyapps.alarm_ui.screens

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.sillyapps.alarm_ui.model.Alarm

interface AlarmStateHolder {

  val alarms: List<Alarm>

  fun toggleAlarm(alarmId: Long)

  fun onAlarmClicked(alarmId: Long)

}