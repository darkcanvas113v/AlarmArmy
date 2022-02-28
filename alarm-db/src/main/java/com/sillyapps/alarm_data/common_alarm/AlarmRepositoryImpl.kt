package com.sillyapps.alarm_data.common_alarm

import com.sillyapps.alarm_data.common_alarm.model.toDataModel
import com.sillyapps.alarm_data.common_alarm.model.toDomainModel
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.common_models.alarm.alarm.Alarm
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.core_di.modules.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
  private val alarmDao: AlarmDao,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
): AlarmRepository {

  override fun observeAlarms(): Flow<List<Alarm>> {
    return alarmDao.observeAll().map { alarms ->
      alarms.map { alarmDto -> alarmDto.toDomainModel() }
    }
  }

  override fun observeAlarm(id: Long): Flow<Alarm> {
    return alarmDao.observeOne(id).map { it.toDomainModel() }
  }

  override suspend fun getAlarm(id: Long): Alarm? {
    return alarmDao.get(id)?.toDomainModel()
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