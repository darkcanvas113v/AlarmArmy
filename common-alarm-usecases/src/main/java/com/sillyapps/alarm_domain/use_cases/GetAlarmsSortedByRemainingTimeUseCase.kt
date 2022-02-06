package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import com.sillyapps.common_models.alarm.calculateRemainingTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAlarmsSortedByRemainingTimeUseCase @Inject constructor(
  private val repository: AlarmRepository
) {
  operator fun invoke(): Flow<List<AlarmWithRemainingTime>> =
    repository.observeAlarms().map { alarms -> alarms.filter { it.active }.map { it.calculateRemainingTime() }.sortedBy { it.remainingTime } }
}