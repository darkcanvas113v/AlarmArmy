package com.sillyapps.alarm_data.current_alarm

import com.sillyapps.alarm_data.current_alarm.model.toDataModel
import com.sillyapps.alarm_data.current_alarm.model.toDomainModel
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentAlarmRepositoryImpl @Inject constructor(
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  @IOCoroutineScope private val ioScope: CoroutineScope,
  private val currentAlarmDataSource: CurrentAlarmDataSource
): CurrentAlarmRepository {

  init {
    ioScope.launch(ioDispatcher) {
      currentAlarmDataSource.load()
    }
  }

  override suspend fun updateCurrentAlarm(newAlarm: AlarmWithRemainingTime?): Unit = withContext(ioDispatcher) {
    currentAlarmDataSource.update(newAlarm?.toDataModel())
  }

  override fun getCurrentAlarm(): Flow<AlarmWithRemainingTime?> = currentAlarmDataSource.observe().map { it?.toDomainModel() }

}