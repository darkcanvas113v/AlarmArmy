package com.sillyapps.profiler_db.repositories

import com.sillyapps.profiler_db.model.ProfilerAlarmDto
import com.sillyapps.profiler_db.model.ProfilerStateDto
import kotlinx.coroutines.flow.Flow

interface ProfilerDataSource {
  suspend fun load()

  fun observeState(): Flow<ProfilerStateDto>
  suspend fun updateState(state: ProfilerStateDto)

  fun observeProfilerAlarms(): Flow<List<ProfilerAlarmDto>>
  suspend fun updateProfilerAlarms(alarms: List<ProfilerAlarmDto>)
}