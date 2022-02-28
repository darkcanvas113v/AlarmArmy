package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import com.sillyapps.alarm_domain.use_cases.UpdateAlarmUseCase
import com.sillyapps.common_models.alarm.alarm.Alarm
import com.sillyapps.core_time.Time
import com.sillyapps.core_time.getMillisAfterStartOfTheDay
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmWatcher @Inject constructor(
  repositoryCurrent: CurrentAlarmRepository,
  getClosestActiveAlarmUseCase: GetClosestActiveAlarmUseCase,
  private val updateAlarmUseCase: UpdateAlarmUseCase,
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
      closestAlarm.drop(1).collect {
        handleUpdateOnAlarms(it)
      }
    }
  }

  suspend fun setFakeAlarm() {
    val alarm = Alarm(
      id = 1,
      time = getMillisAfterStartOfTheDay() + 10 * Time.s,
      active = true,
      weekDays = 0,
      repeat = false)

    updateAlarmUseCase(alarm)
  }

  private suspend fun handleUpdateOnAlarms(newAlarm: AlarmWithRemainingTime?) {
    if (newAlarm == null) {
      alarmSetter?.cancelAlarm()
      return
    }

    val currentAlarm = activeAlarm.first()

    if (!newAlarm.isSameAs(currentAlarm)) {
      alarmSetter?.setAlarm(newAlarm)
    }
  }

}