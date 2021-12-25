package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class AlarmScheduler @Inject constructor(
  private val repository: AlarmSchedulerRepository
) {

  private var alarmService: WeakReference<AlarmSchedulerService>? = null

  private val activeAlarm: Flow<SchedulerAlarm?> = repository.getQueue().map { if (it.isNotEmpty()) it[0] else null }

  fun initialize(service: AlarmSchedulerService) {
    alarmService = WeakReference(service)

    service.scope.launch {
      repository.loadQueue()

      repository.getAlarms().drop(1).collect {
        handleUpdateOnAlarms(it)
      }
    }
  }

  private suspend fun handleUpdateOnAlarms(alarms: List<SchedulerAlarm>) {
    val sortedAlarms = alarms.filter { it.active }.sortedBy { it.remainingTime }

    val currentAlarm = activeAlarm.first()
    val newAlarm = sortedAlarms.firstOrNull()

    if (newAlarm == null) {
      alarmService?.get()?.cancelAlarm()
      repository.updateQueue(sortedAlarms)
      return
    }

    if (!newAlarm.isSameAs(currentAlarm)) {
      alarmService?.get()?.setAlarm(newAlarm.remainingTime)
    }
    repository.updateQueue(sortedAlarms)
  }

}