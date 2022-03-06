package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import com.sillyapps.alarm_domain.use_cases.UpdateAlarmUseCase
import com.sillyapps.common_models.alarm.alarm.Alarm
import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.toAlarm
import com.sillyapps.common_profiler_usecases.use_cases.*
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

  getProfilerOffsetsUseCase: GetProfilerOffsetsUseCase,
  getProfilerStateUseCase: GetProfilerStateUseCase,

  private val updateAlarmUseCase: UpdateAlarmUseCase,
  private val updateProfilerAlarmsUseCase: UpdateProfilerAlarmsUseCase
  ) {

  private val closestAlarm = getClosestActiveAlarmUseCase()
  private var alarmSetter: AlarmSetter? = null

  private val activeAlarm: Flow<AlarmWithRemainingTime?> = repositoryCurrent.getCurrentAlarm()

  private val profilerIsEnabled = getProfilerStateUseCase().map { it.enabled }
  private val profilerOffsets = getProfilerOffsetsUseCase()

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
    scope.launch {
      profilerIsEnabled.drop(1).collect {
        handleUpdateOnProfiler()
      }
    }
    scope.launch {
      profilerOffsets.drop(1).collect {
        handleUpdateOnProfiler()
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
      if (profilerIsEnabled.first()) {
        setAlarmAccordingToProfiler(newAlarm)
        return
      }

      alarmSetter?.setAlarm(newAlarm)
    }
  }

  private suspend fun handleUpdateOnProfiler() {
    val currentAlarm = activeAlarm.first() ?: return

    if (!profilerIsEnabled.first()) {
      alarmSetter?.setAlarm(currentAlarm)
      return
    }

    setAlarmAccordingToProfiler(currentAlarm)
  }

  private suspend fun setAlarmAccordingToProfiler(currentAlarm: AlarmWithRemainingTime) {
    val alarms = buildProfilerAlarms(currentAlarm)

    updateProfilerAlarmsUseCase(alarms = alarms)

    // To solve problem then alarm is out of range
    val firstValidAlarm = alarms.first { it.time <= System.currentTimeMillis() }
    alarmSetter?.setAlarm(currentAlarm, firstValidAlarm.time)
  }

  private suspend fun buildProfilerAlarms(currentAlarm: AlarmWithRemainingTime): List<ProfilerAlarm> {
    return profilerOffsets.first().map { it.toAlarm(currentAlarm.startupTime) }.sortedBy { it.time }
  }

}