package com.sillyapps.alarm_domain.model

data class AlarmWithRemainingTime(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val repeat: Int,
  val remainingTime: Long
) {
  fun isSameAs(other: AlarmWithRemainingTime?): Boolean {
    if (other == null) return false

    return id == other.id && time == other.time && active == other.active && repeat == other.repeat
  }
}