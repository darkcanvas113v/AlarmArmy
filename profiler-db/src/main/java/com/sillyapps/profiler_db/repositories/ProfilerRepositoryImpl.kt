package com.sillyapps.profiler_db.repositories

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.profiler_db.model.toCommonModel
import com.sillyapps.profiler_db.model.toDataModel
import com.sillyapps.profiler_db.persistence.ProfilerDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProfilerRepositoryImpl @Inject constructor(
  private val profilerDao: ProfilerDao
): ProfilerRepository {
  override fun getProfilerAlarms(): Flow<List<ProfilerAlarm>> {
    return profilerDao.observeAll().map { alarms -> alarms.map { it.toCommonModel() } }
  }

  override suspend fun deleteProfilerAlarm(profilerAlarm: ProfilerAlarm) {
    profilerDao.delete(profilerAlarm.toDataModel())
  }

  override suspend fun upsertProfilerAlarm(profilerAlarm: ProfilerAlarm) {
    profilerDao.upsert(profilerAlarm.toDataModel())
  }


}