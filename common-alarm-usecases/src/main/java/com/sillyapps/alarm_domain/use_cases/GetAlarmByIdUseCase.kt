package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.common_models.alarm.alarm.Alarm
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  suspend operator fun invoke(id: Long): Alarm? {
    return repository.getAlarm(id)
  }

}