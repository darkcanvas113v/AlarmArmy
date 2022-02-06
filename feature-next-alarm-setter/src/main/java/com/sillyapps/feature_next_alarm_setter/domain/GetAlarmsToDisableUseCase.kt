package com.sillyapps.feature_next_alarm_setter.domain

import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.common_models.alarm.calculateRemainingTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlarmsToDisableUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  operator fun invoke(startingTime: Long): Flow<List<AlarmWithRemainingTime>> {
    val currentTime = System.currentTimeMillis()
    return repository.observeAlarms().map { alarms ->
      alarms
        .filter { it.active && !it.repeat }
        .map {
          it.calculateRemainingTime(startingTime) }
        .filter {
          it.startupTime <= currentTime + EPS }
    }
  }

  companion object {
    const val EPS = 10000L
  }

}