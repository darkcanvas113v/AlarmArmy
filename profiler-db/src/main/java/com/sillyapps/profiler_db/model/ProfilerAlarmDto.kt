package com.sillyapps.profiler_db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.squareup.moshi.JsonClass

@Entity(tableName = "profiler_alarm")
data class ProfilerAlarmDto(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val offset: Long
)

fun ProfilerAlarmDto.toCommonModel(): ProfilerAlarm {
  return ProfilerAlarm(id, offset)
}

fun ProfilerAlarm.toDataModel(): ProfilerAlarmDto {
  return ProfilerAlarmDto(id, offset)
}