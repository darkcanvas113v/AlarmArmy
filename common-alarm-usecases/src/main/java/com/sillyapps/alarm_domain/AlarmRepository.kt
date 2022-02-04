package com.sillyapps.alarm_domain

import com.sillyapps.common_model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<Alarm>>

    fun getAlarm(id: Long): Flow<Alarm>

    suspend fun updateAlarm(alarm: Alarm)

    suspend fun saveAlarm(alarm: Alarm)

    suspend fun upsert(alarm: Alarm)

}