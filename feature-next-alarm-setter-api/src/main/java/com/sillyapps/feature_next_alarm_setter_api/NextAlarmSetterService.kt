package com.sillyapps.feature_next_alarm_setter_api

interface NextAlarmSetterService {
  fun disableAlarm(alarmIsSetCallback: () -> Unit)

  fun doze(alarmIsSetCallback: () -> Unit)

  interface Binder {
    fun getService(): NextAlarmSetterService
  }
}