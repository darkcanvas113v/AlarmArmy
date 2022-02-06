package com.sillyapps.feature_alarm_setter_api

import com.sillyapps.common_models.alarm.AlarmWithRemainingTime

interface AlarmSetter {
  fun setAlarm(alarm: AlarmWithRemainingTime)
  fun cancelAlarm()
}