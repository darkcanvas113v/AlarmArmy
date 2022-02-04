package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_domain.model.Alarm
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  suspend operator fun invoke(id: Long): Alarm? {
    return repository.getAlarm(id)
  }

}