package com.sillyapps.profiler_db.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset

@Entity(tableName = "profiler_alarm")
data class ProfilerOffsetDto(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val offset: Long
)

fun ProfilerOffsetDto.toCommonModel(): ProfilerOffset {
  return ProfilerOffset(id, offset)
}

fun ProfilerOffset.toDataModel(): ProfilerOffsetDto {
  return ProfilerOffsetDto(id, offset)
}