package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmWatcher @Inject constructor(
  private val repositoryCurrent: CurrentAlarmRepository,
  getClosestActiveAlarmUseCase: GetClosestActiveAlarmUseCase
) {

  private val closestAlarm = getClosestActiveAlarmUseCase()
  private var alarmSetter: AlarmSetter? = null

  private val activeAlarm: Flow<AlarmWithRemainingTime?> = repositoryCurrent.getCurrentAlarm()

  fun initialize(
    service: AlarmSetter,
    scope: CoroutineScope
  ) {
    alarmSetter = service

    scope.launch {
      repositoryCurrent.loadAlarm()

      closestAlarm.drop(1).collect {
        handleUpdateOnAlarms(it)
      }
    }
  }

  private suspend fun handleUpdateOnAlarms(newAlarm: AlarmWithRemainingTime?) {
    if (newAlarm == null) {
      alarmSetter?.cancelAlarm()
      repositoryCurrent.updateCurrentAlarm(null)
      return
    }

    val currentAlarm = activeAlarm.first()

    if (!newAlarm.isSameAs(currentAlarm)) {
      alarmSetter?.setAlarm(newAlarm.remainingTime)
      repositoryCurrent.updateCurrentAlarm(newAlarm)
    }
  }

}