package com.sillyapps.alarm_ui.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.sillyapps.alarm_ui.model.Alarm
import javax.inject.Inject

class AlarmListViewModel @Inject constructor(): ViewModel(), AlarmStateHolder {

  override val alarms = mutableStateListOf<Alarm>()

  fun toggleAlarmActive(alarmId: Long) {
    val alarm = alarms.find { it.id == alarmId }
    alarm?.let {
    }
  }

  override fun toggleAlarm(alarmId: Long) {
    TODO("Not yet implemented")
  }

  override fun onAlarmClicked(alarmId: Long) {
    TODO("Not yet implemented")
  }


}