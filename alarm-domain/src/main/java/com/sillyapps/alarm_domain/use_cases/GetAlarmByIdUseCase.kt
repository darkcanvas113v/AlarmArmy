package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.AlarmRepository
import javax.inject.Inject

class GetAlarmByIdUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  operator fun invoke(id: Long) {
    repository.getAlarm(id)
  }

}