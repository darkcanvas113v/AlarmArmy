package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.use_cases.GetAlarmsSortedByRemainingTimeUseCase
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import javax.inject.Inject

class AlarmWatcher @Inject constructor(
  private val repository: AlarmWatcherRepository,
  getClosestActiveAlarmUseCase: GetClosestActiveAlarmUseCase
) {

  private val closestAlarm = getClosestActiveAlarmUseCase()
  private var alarmService: WeakReference<AlarmSetterService>? = null

  private val activeAlarm: Flow<AlarmWithRemainingTime?> = repository.getCurrentAlarm()

  fun initialize(service: AlarmSetterService) {
    alarmService = WeakReference(service)

    service.scope.launch {
      repository.loadAlarm()

      closestAlarm.drop(1).collect {
        handleUpdateOnAlarms(it)
      }
    }
  }

  private suspend fun handleUpdateOnAlarms(newAlarm: AlarmWithRemainingTime?) {
    if (newAlarm == null) {
      alarmService?.get()?.cancelAlarm()
      repository.updateCurrentAlarm(null)
      return
    }

    val currentAlarm = activeAlarm.first()

    if (!newAlarm.isSameAs(currentAlarm)) {
      alarmService?.get()?.setAlarm(newAlarm.remainingTime)
      repository.updateCurrentAlarm(newAlarm)
    }
  }

}