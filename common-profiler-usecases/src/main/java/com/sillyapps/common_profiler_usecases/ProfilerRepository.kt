package com.sillyapps.common_profiler_usecases

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_models.alarm.profiler.ProfilerState
import kotlinx.coroutines.flow.Flow

interface ProfilerRepository {

  fun getProfilerOffsets(): Flow<List<ProfilerOffset>>

  suspend fun deleteProfilerOffset(profilerOffset: ProfilerOffset)

  suspend fun upsertProfilerOffset(profilerOffset: ProfilerOffset)

  suspend fun setProfilerState(state: ProfilerState)

  fun getProfilerState(): Flow<ProfilerState>

  fun getProfilerAlarms(): Flow<List<ProfilerAlarm>>
  suspend fun updateProfilerAlarms(alarms: List<ProfilerAlarm>)

}