package com.sillyapps.feature_next_alarm_setter.domain

import com.sillyapps.common_models.alarm.toAlarm
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DisableAllSkippedAlarmUseCase @Inject constructor(
  private val currentAlarmRepository: CurrentAlarmRepository,
  private val alarmRepository: AlarmRepository,
  private val getAlarmsToDisableUseCase: GetAlarmsToDisableUseCase
) {

  suspend operator fun invoke() {
    val currentAlarm = currentAlarmRepository.getCurrentAlarm().first() ?: return

    val alarmsToBeDisabled = getAlarmsToDisableUseCase(currentAlarm.startupTime).first()

    for (alarm in alarmsToBeDisabled) {
      alarmRepository.updateAlarm(
        alarm.toAlarm(overrideActive = false))
    }
  }

}