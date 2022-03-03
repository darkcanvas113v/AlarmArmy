package com.sillyapps.profiler_db.repositories

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.profiler_db.model.toCommonModel
import com.sillyapps.profiler_db.model.toDataModel
import com.sillyapps.profiler_db.persistence.ProfilerDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProfilerRepositoryImpl @Inject constructor(
  private val profilerDao: ProfilerDao,
  private val profilerDataSource: ProfilerDataSource,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @IOCoroutineScope private val ioScope: CoroutineScope,
): ProfilerRepository {

  init {
    ioScope.launch(ioDispatcher) {
      profilerDataSource.load()
    }
  }

  override fun getProfilerAlarms(): Flow<List<ProfilerAlarm>> {
    return profilerDao.observeAll().map { alarms -> alarms.map { it.toCommonModel() } }
  }

  override suspend fun deleteProfilerAlarm(profilerAlarm: ProfilerAlarm) = withContext(ioDispatcher) {
    profilerDao.delete(profilerAlarm.toDataModel())
  }

  override suspend fun upsertProfilerAlarm(profilerAlarm: ProfilerAlarm) = withContext(ioDispatcher) {
    profilerDao.upsert(profilerAlarm.toDataModel())
  }

  override suspend fun setProfilerState(state: ProfilerState) = withContext(ioDispatcher) {
    profilerDataSource.update(state.toDataModel())
  }

  override fun getProfilerState(): Flow<ProfilerState> {
    return profilerDataSource.observe().map { it.toCommonModel() }
  }


}