package com.sillyapps.alarm_data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sillyapps.alarm_domain.model.Alarm

@Entity(tableName = "alarms")
data class AlarmDto(
  @PrimaryKey(autoGenerate = true) val id: Long,
  val time: Long,
  val active: Boolean,
  val repeat: Int
)

internal fun AlarmDto.toDomainModel(): Alarm {
  return Alarm(id, time, active, repeat)
}

internal fun Alarm.toDataModel(): AlarmDto {
  return AlarmDto(id, time, active, repeat)
}