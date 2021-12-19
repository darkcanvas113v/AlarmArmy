package com.sillyapps.alarm_scheduler.domain

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmSchedulerRepository {

  fun getQueue(): Flow<List<SchedulerAlarm>>

  suspend fun updateQueue(newQueue: List<SchedulerAlarm>)

  suspend fun loadQueue()

}