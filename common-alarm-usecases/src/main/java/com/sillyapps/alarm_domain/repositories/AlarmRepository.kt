package com.sillyapps.alarm_domain.repositories

import com.sillyapps.common_models.alarm.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun observeAlarms(): Flow<List<Alarm>>

    fun observeAlarm(id: Long): Flow<Alarm>

    suspend fun getAlarm(id: Long): Alarm?

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun saveAlarm(alarm: Alarm)

    suspend fun upsert(alarm: Alarm)

}