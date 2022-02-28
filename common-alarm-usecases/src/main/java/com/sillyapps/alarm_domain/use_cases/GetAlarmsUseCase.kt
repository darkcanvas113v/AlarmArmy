package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.common_models.alarm.alarm.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  operator fun invoke(): Flow<List<Alarm>> {
    return repository.observeAlarms()
  }

}