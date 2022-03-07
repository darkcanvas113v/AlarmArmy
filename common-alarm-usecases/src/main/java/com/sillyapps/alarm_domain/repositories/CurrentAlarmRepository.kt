package com.sillyapps.alarm_domain.repositories

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import kotlinx.coroutines.flow.Flow

interface CurrentAlarmRepository {

  fun getCurrentAlarm(): Flow<AlarmWithRemainingTime?>

  suspend fun updateCurrentAlarm(newAlarmWithRemainingTime: AlarmWithRemainingTime?)

}