package com.sillyapps.alarm_scheduler.data

import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import com.sillyapps.common_model.Alarm

internal fun Alarm.toDomainModel(): SchedulerAlarm {
  return SchedulerAlarm(
    id = id,
    time = time,
    active = active,
    repeat = repeat,
    remainingTime = getRemainingTime(repeat, time)
  )
}