package com.sillyapps.common_profiler_usecases

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerState
import kotlinx.coroutines.flow.Flow

interface ProfilerRepository {

  fun getProfilerAlarms(): Flow<List<ProfilerAlarm>>

  suspend fun deleteProfilerAlarm(profilerAlarm: ProfilerAlarm)

  suspend fun upsertProfilerAlarm(profilerAlarm: ProfilerAlarm)

  suspend fun setProfilerState(state: ProfilerState)

  fun getProfilerState(): Flow<ProfilerState>

}