package com.sillyapps.alarm_scheduler.data.model

import com.sillyapps.alarm_domain.model.AlarmWithRemainingTime
import com.sillyapps.core_time.getRemainingTime
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AlarmData(
  val id: Long,
  val time: Long,
  val repeat: Int
)

fun AlarmWithRemainingTime.toDataModel(): AlarmData {
  return AlarmData(id, time, repeat)
}

fun AlarmData.toDomainModel(): AlarmWithRemainingTime {
  return AlarmWithRemainingTime(
    id = id,
    time = time,
    active = true,
    repeat = repeat,
    remainingTime = getRemainingTime(repeat, time)
  )
}