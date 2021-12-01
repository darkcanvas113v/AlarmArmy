package com.sillyapps.alarm_domain.repository

import com.sillyapps.alarm_domain.model.Alarm

interface AlarmRepository {

    suspend fun getAlarms(): Alarm

    suspend fun saveAlarm(alarm: Alarm)

}