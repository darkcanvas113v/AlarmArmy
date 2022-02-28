package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetClosestActiveAlarmUseCase @Inject constructor(
  private val getAlarmsSortedByRemainingTimeUseCase: GetAlarmsSortedByRemainingTimeUseCase
) {
  operator fun invoke(): Flow<AlarmWithRemainingTime?> {
    return getAlarmsSortedByRemainingTimeUseCase().map { it.firstOrNull() }
  }
}