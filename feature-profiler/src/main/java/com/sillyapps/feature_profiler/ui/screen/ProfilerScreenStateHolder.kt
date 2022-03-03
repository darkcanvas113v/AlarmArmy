package com.sillyapps.feature_profiler.ui.screen

import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm
import kotlinx.coroutines.flow.Flow

interface ProfilerScreenStateHolder {
  fun getProfilerAlarms(): Flow<List<UIProfilerAlarm>>

  fun updateProfilerAlarm(profilerAlarm: UIProfilerAlarm)
  fun deleteProfilerAlarm(profilerAlarm: UIProfilerAlarm)

  fun setProfilerState(state: Boolean)
  fun getProfilerState(): Flow<Boolean>
}