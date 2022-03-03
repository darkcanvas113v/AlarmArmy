package com.sillyapps.profiler_db.repositories

import com.sillyapps.profiler_db.model.ProfilerStateDto
import kotlinx.coroutines.flow.Flow

interface ProfilerDataSource {
  suspend fun load()
  fun observe(): Flow<ProfilerStateDto>
  suspend fun update(state: ProfilerStateDto)
}