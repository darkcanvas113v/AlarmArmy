package com.sillyapps.alarm_ui.domain

import com.sillyapps.alarm_domain.AlarmRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleAlarmUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  suspend operator fun invoke(alarmId: Long) {
    val alarm = repository.getAlarm(alarmId).first()

    val toggledAlarm = alarm.copy(active = !alarm.active)

    repository.updateAlarm(toggledAlarm)
  }

}