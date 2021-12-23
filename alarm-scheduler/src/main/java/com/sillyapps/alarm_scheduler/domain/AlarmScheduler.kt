package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmScheduler @Inject constructor(
  private val repository: AlarmSchedulerRepository
) {

  private var alarmService: AlarmSchedulerService? = null

  fun initialize(service: AlarmSchedulerService) {
    alarmService = service

    service.scope.launch {
      repository.loadQueue()

      repository.getAlarms().collect {
        handleUpdateOnAlarms(it)
      }
    }

  }

  private suspend fun handleUpdateOnAlarms(alarms: List<SchedulerAlarm>) {
    alarms.sortedBy { it.remainingTime }
  }

}