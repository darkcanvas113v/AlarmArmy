package com.sillyapps.profiler_db.repositories

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.profiler_db.model.ProfilerOffsetDto
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

      // This line will make sure that the target alarm(profilerAlarm with offset=0) is always in the database
      profilerDao.insert(ProfilerOffsetDto(1, 0L))
    }
  }

  override fun getProfilerOffsets(): Flow<List<ProfilerOffset>> {
    return profilerDao.observeAll().map { alarms -> alarms.map { it.toCommonModel() } }
  }

  override suspend fun deleteProfilerOffset(profilerOffset: ProfilerOffset) = withContext(ioDispatcher) {
    profilerDao.delete(profilerOffset.toDataModel())
  }

  override suspend fun upsertProfilerOffset(profilerOffset: ProfilerOffset) = withContext(ioDispatcher) {
    profilerDao.upsert(profilerOffset.toDataModel())
  }

  override suspend fun setProfilerState(state: ProfilerState) = withContext(ioDispatcher) {
    profilerDataSource.updateState(state.toDataModel())
  }

  override fun getProfilerState(): Flow<ProfilerState> {
    return profilerDataSource.observeState().map { it.toCommonModel() }
  }

  override fun getProfilerAlarms(): Flow<List<ProfilerAlarm>> {
    return profilerDataSource.observeProfilerAlarms().map { it.map { alarm -> alarm.toCommonModel() } }
  }

  override suspend fun updateProfilerAlarms(alarms: List<ProfilerAlarm>) = withContext(ioDispatcher) {
    profilerDataSource.updateProfilerAlarms(alarms.map { it.toDataModel() })
  }


}