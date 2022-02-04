package com.sillyapps.alarm_domain.use_cases

import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.common_model.Alarm
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateAlarmUseCase @Inject constructor(
  private val repository: AlarmRepository
) {

  suspend operator fun invoke(alarm: Alarm) {
    return repository.upsert(alarm)
  }
}