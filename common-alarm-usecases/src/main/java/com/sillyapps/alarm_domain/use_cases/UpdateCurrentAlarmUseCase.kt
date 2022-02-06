package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateCurrentAlarmUseCase @Inject constructor(
  private val repository: CurrentAlarmRepository
) {
  suspend operator fun invoke(alarm: AlarmWithRemainingTime?) {
    return repository.updateCurrentAlarm(alarm)
  }
}