package com.sillyapps.alarm_data.repository

import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.model.toDataModel
import com.sillyapps.alarm_data.model.toDomainModel
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_domain.alarm_list.AlarmRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
  private val alarmDao: AlarmDao,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
): AlarmRepository {

  override fun getAlarms(): Flow<List<Alarm>> {
    return alarmDao.observeAll().map { alarms ->
      alarms.map { alarmDto -> alarmDto.toDomainModel() }
    }
  }

  override fun getAlarm(id: Long): Flow<Alarm> {
    return alarmDao.observeOne(id).map { it.toDomainModel() }
  }

  override suspend fun saveAlarm(alarm: Alarm) {
    withContext(ioDispatcher) {
      alarmDao.insert(alarm.toDataModel())
    }
  }

  override suspend fun upsert(alarm: Alarm) = withContext(ioDispatcher) {
    alarmDao.upsert(alarm.toDataModel())
  }

  override suspend fun updateAlarm(alarm: Alarm) = withContext(ioDispatcher) {
      alarmDao.update(alarm.toDataModel())
  }

}