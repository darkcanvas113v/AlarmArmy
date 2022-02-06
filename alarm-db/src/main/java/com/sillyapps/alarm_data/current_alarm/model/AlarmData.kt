package com.sillyapps.alarm_data.current_alarm.model

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import com.sillyapps.core_time.getRemainingTime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmData(
  val id: Long,
  val time: Long,
  val weekDays: Int,
  val repeat: Boolean,
  val startupTime: Long
)

fun AlarmWithRemainingTime.toDataModel(): AlarmData {
  return AlarmData(id, time, weekDays, repeat, startupTime)
}

fun AlarmData.toDomainModel(): AlarmWithRemainingTime {
  val from = startupTime
  val remainingTime = getRemainingTime(weekDays, time, from)
  return AlarmWithRemainingTime(
    id = id,
    time = time,
    active = true,
    weekDays = weekDays,
    remainingTime = remainingTime,
    startupTime = from + remainingTime,
    repeat = repeat
  )
}