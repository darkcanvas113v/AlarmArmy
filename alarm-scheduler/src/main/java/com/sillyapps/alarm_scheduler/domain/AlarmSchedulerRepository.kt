package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmSchedulerRepository {

  fun getAlarms(): Flow<List<SchedulerAlarm>>

  fun getCurrentAlarm(): Flow<SchedulerAlarm?>

  suspend fun updateCurrentAlarm(newAlarm: SchedulerAlarm?)

  suspend fun loadAlarm()

}