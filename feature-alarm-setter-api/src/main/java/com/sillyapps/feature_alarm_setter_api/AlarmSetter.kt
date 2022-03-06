package com.sillyapps.feature_alarm_setter_api

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime

interface AlarmSetter {
  fun setAlarm(alarm: AlarmWithRemainingTime, time: Long = alarm.startupTime)
  fun cancelAlarm()
}