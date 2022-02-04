package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.AlarmRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  // TODO это можно в самой базе данных реализовать, но пока пусть так останется
  suspend operator fun invoke(alarmId: Long) {
    val alarm = repository.getAlarm(alarmId).first()

    val toggledAlarm = alarm.copy(active = !alarm.active)

    repository.updateAlarm(toggledAlarm)
  }

}