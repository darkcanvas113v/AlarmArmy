package com.sillyapps.feature_next_alarm_setter.domain

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_profiler_usecases.use_cases.GetProfilerAlarmsUseCase
import com.sillyapps.common_profiler_usecases.use_cases.GetProfilerStateUseCase
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetNextAlarmByDozeUseCase @Inject constructor(
  private val repository: NextAlarmRepository,
  private val getProfilerStateUseCase: GetProfilerStateUseCase,
  private val getProfilerAlarmsUseCase: GetProfilerAlarmsUseCase
) {

  suspend operator fun invoke(): ProfilerAlarm {
    // TODO check profile alarm
    if (getProfilerStateUseCase().first().enabled) {
      return getDozeAlarmAccordingToProfiler()
    }

    return getDefaultDozeAlarm()
  }

  private suspend fun getDozeAlarmAccordingToProfiler(): ProfilerAlarm {
    val profilerAlarms = getProfilerAlarmsUseCase().first()

    return profilerAlarms.first { it.time > System.currentTimeMillis() }
  }

  private suspend fun getDefaultDozeAlarm(): ProfilerAlarm {
    val dozeDuration = repository.getDozeDuration()
    return ProfilerAlarm(
      time = System.currentTimeMillis() + dozeDuration,
      state = ProfilerAlarm.State.TARGET
    )
  }

}