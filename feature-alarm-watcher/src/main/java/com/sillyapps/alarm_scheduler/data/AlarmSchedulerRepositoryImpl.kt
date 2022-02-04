package com.sillyapps.alarm_scheduler.data

import android.content.Context
import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_scheduler.data.model.toDataModel
import com.sillyapps.alarm_scheduler.data.model.toDomainModel
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerRepository
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmSchedulerRepositoryImpl @Inject constructor(
  context: Context,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  private val alarmDao: AlarmDao,
  private val currentAlarmDataSource: CurrentAlarmDataSource
): AlarmSchedulerRepository {

  override suspend fun loadAlarm() = withContext(ioDispatcher) {
    currentAlarmDataSource.load()
  }

  override suspend fun updateCurrentAlarm(newAlarm: SchedulerAlarm?): Unit = withContext(ioDispatcher) {
    currentAlarmDataSource.update(newAlarm?.toDataModel())
  }

  override fun getAlarms(): Flow<List<SchedulerAlarm>> = alarmDao.observeAll().map { it.map { alarm -> alarm.toDomainModel() } }

  override fun getCurrentAlarm(): Flow<SchedulerAlarm?> = currentAlarmDataSource.observe().map { it?.toDomainModel() }

}