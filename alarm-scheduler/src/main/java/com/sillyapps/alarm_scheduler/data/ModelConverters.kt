package com.sillyapps.alarm_scheduler.data

import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm

internal fun AlarmDto.toDomainModel(): SchedulerAlarm {
  return SchedulerAlarm(id, time, active, repeat)
}