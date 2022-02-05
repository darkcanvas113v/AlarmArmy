package com.sillyapps.feature_next_alarm_setter.domain

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import javax.inject.Inject

class GetNextAlarmByDozeUseCase @Inject constructor(
  private val repository: NextAlarmRepository
) {

  suspend operator fun invoke(): AlarmWithRemainingTime? {
    // TODO check profile alarm
    return AlarmWithRemainingTime(
      id = 0,
      time = 0,
      active = false,
      weekDays = 0,
      repeat = false,
      remainingTime = repository.getDozeDuration())
  }

}