package com.sillyapps.alarm_domain.alarm_list

import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AlarmListInteractor @Inject constructor(
  private val repository: AlarmRepository
) {

  private val alarms = repository.getAlarms()

  suspend fun toggleAlarmActive(alarmId: Long) {
    val alarm = alarms.first().find { it.id == alarmId }
    requireNotNull(alarm)

    val toggledAlarm = alarm.copy(active = !alarm.active)

    repository.updateAlarm(toggledAlarm)
  }

  fun getAlarms(): Flow<List<Alarm>> = alarms

}