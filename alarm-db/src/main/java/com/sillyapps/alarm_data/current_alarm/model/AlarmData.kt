package com.sillyapps.alarm_data.current_alarm.model

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import com.sillyapps.core_time.getRemainingTime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmData(
  val id: Long,
  val time: Long,
  val weekDays: Int,
  val repeat: Boolean
)

fun AlarmWithRemainingTime.toDataModel(): AlarmData {
  return AlarmData(id, time, weekDays, repeat)
}

fun AlarmData.toDomainModel(): AlarmWithRemainingTime {
  return AlarmWithRemainingTime(
    id = id,
    time = time,
    active = true,
    weekDays = weekDays,
    remainingTime = getRemainingTime(weekDays, time),
    repeat = repeat
  )
}