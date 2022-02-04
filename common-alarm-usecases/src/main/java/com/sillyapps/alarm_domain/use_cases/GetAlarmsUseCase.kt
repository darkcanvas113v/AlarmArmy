package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlarmsUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  operator fun invoke(): Flow<List<Alarm>> {
    return repository.observeAlarms()
  }

}