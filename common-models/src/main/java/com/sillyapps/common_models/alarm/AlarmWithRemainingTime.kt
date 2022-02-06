package com.sillyapps.common_models.alarm

data class AlarmWithRemainingTime(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val weekDays: Int,
  val repeat: Boolean,
  val remainingTime: Long,
  val startupTime: Long
) {
  fun isSameAs(other: AlarmWithRemainingTime?): Boolean {
    if (other == null) return false

    return id == other.id && time == other.time && active == other.active && weekDays == other.weekDays && repeat == other.repeat
  }
}