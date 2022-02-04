package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import kotlinx.coroutines.flow.Flow

interface AlarmWatcherRepository {

  fun getCurrentAlarm(): Flow<AlarmWithRemainingTime?>

  suspend fun updateCurrentAlarm(newAlarm: AlarmWithRemainingTime?)

  suspend fun loadAlarm()

}