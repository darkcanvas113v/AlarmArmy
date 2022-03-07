package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import javax.inject.Inject

class UpdateCurrentAlarmUseCase @Inject constructor(
  private val repository: CurrentAlarmRepository
) {
  suspend operator fun invoke(alarmWithRemainingTime: AlarmWithRemainingTime?) {
    return repository.updateCurrentAlarm(alarmWithRemainingTime)
  }
}