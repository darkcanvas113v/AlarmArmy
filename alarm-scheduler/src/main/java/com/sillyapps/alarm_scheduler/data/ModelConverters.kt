package com.sillyapps.alarm_scheduler.data

import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import com.sillyapps.core.AlarmConstants
import com.sillyapps.core.convertToMillis
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime

internal fun AlarmDto.toDomainModel(): SchedulerAlarm {
  return SchedulerAlarm(
    id = id,
    time = time,
    active = active,
    repeat = repeat,
    remainingTime = getRemainingTime(repeat, time)
  )
}