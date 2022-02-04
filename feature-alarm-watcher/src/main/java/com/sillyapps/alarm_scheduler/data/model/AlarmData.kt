package com.sillyapps.alarm_scheduler.data.model

import com.sillyapps.alarm_scheduler.data.getRemainingTime
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmData(
  val id: Long,
  val time: Long,
  val repeat: Int
)

fun SchedulerAlarm.toDataModel(): AlarmData {
  return AlarmData(id, time, repeat)
}

fun AlarmData.toDomainModel(): SchedulerAlarm {
  return SchedulerAlarm(
    id = id,
    time = time,
    active = true,
    repeat = repeat,
    remainingTime = getRemainingTime(repeat, time)
  )
}