package com.sillyapps.alarm_scheduler.domain

import kotlinx.coroutines.CoroutineScope

interface AlarmSetterService {
  val scope: CoroutineScope

  fun setAlarm(triggerTime: Long)

  fun cancelAlarm()
}