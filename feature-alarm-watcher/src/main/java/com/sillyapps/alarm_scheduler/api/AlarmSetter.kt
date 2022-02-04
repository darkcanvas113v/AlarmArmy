package com.sillyapps.alarm_scheduler.api

interface AlarmSetter {
  fun setAlarm(triggerTime: Long)
  fun cancelAlarm()
}