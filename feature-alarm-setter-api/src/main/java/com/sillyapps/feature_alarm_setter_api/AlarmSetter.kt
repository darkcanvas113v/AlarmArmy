package com.sillyapps.feature_alarm_setter_api

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime

interface AlarmSetter {
  fun setMainAlarm(alarmWithRemainingTime: AlarmWithRemainingTime)
  fun setAlarm(time: Long)
  fun cancelAlarm()
}