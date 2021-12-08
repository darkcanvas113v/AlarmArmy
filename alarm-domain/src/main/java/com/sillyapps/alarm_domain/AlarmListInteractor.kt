package com.sillyapps.alarm_domain

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AlarmListInteractor @Inject constructor(
  private val repository: AlarmRepository
) {

  private val alarms = repository.getAlarms()

  suspend fun toggleAlarmActive(alarmId: Long) {
    val alarm = alarms.first().find { it.id == alarmId }
    requireNotNull(alarm)

    val toggledAlarm = Alarm(alarm.id, alarm.time, !alarm.active, alarm.repeat)

    repository.updateAlarm(toggledAlarm)
  }

  fun getAlarms(): Flow<List<Alarm>> = alarms

}