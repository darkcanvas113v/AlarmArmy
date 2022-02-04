package com.sillyapps.feature_next_alarm_setter.domain

import javax.inject.Inject

class GetNextAlarmByDozeUseCase @Inject constructor(
  private val repository: NextAlarmRepository
) {

  suspend operator fun invoke(): Long? {
    // TODO check profile alarm
    return repository.getDozeDuration()
  }

}