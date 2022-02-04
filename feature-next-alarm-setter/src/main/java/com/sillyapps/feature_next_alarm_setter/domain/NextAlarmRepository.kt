package com.sillyapps.feature_next_alarm_setter.domain

interface NextAlarmRepository {
  suspend fun getDozeDuration(): Long
}