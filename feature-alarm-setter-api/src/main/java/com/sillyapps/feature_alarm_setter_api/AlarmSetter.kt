package com.sillyapps.feature_alarm_setter_api

interface AlarmSetter {
  fun setAlarm(triggerTime: Long)
  fun cancelAlarm()
}