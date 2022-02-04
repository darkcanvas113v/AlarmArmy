package com.sillyapps.alarm_scheduler.data

import com.sillyapps.alarm_domain.use_cases.GetAlarmsUseCase
import com.sillyapps.alarm_scheduler.data.model.toDataModel
import com.sillyapps.alarm_scheduler.data.model.toDomainModel
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerRepository
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import com.sillyapps.core_di.modules.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmSchedulerRepositoryImpl @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  private val getAlarmsUseCase: GetAlarmsUseCase,
  private val currentAlarmDataSource: CurrentAlarmDataSource
): AlarmSchedulerRepository {

  override suspend fun loadAlarm() = withContext(ioDispatcher) {
    currentAlarmDataSource.load()
  }

  override suspend fun updateCurrentAlarm(newAlarm: SchedulerAlarm?): Unit = withContext(ioDispatcher) {
    currentAlarmDataSource.update(newAlarm?.toDataModel())
  }

  override fun getAlarms(): Flow<List<SchedulerAlarm>> = getAlarmsUseCase().map { it.map { alarm -> alarm.toDomainModel() } }

  override fun getCurrentAlarm(): Flow<SchedulerAlarm?> = currentAlarmDataSource.observe().map { it?.toDomainModel() }

}