package com.sillyapps.alarm_scheduler.domain

import javax.inject.Inject

class AlarmSchedulerInteractor @Inject constructor(
  private val alarmSchedulerRepository: AlarmSchedulerRepository
) {

  suspend fun saveQueue() {

  }

}