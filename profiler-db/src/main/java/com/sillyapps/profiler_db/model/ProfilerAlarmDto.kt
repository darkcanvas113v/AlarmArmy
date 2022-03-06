package com.sillyapps.profiler_db.model

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfilerAlarmDto(
  val time: Long,
  val state: Int
)

fun ProfilerAlarmDto.toCommonModel(): ProfilerAlarm {
  return ProfilerAlarm(
    time = time,
    state = ProfilerAlarm.State.fromInt(state)
  )
}

fun ProfilerAlarm.toDataModel(): ProfilerAlarmDto {
  return ProfilerAlarmDto(
    time = time,
    state = state.ordinal
  )
}