package com.sillyapps.alarm_domain.repositories

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import kotlinx.coroutines.flow.Flow

interface CurrentAlarmRepository {

  fun getCurrentAlarm(): Flow<AlarmWithRemainingTime?>

  suspend fun updateCurrentAlarm(newAlarm: AlarmWithRemainingTime?)

  suspend fun loadAlarm()

}