package com.sillyapps.alarm_scheduler.domain.model

data class SchedulerAlarm(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val repeat: Int,
  val remainingTime: Long
) {
  fun isSameAs(other: SchedulerAlarm?): Boolean {
    if (other == null) return false

    return id == other.id && time == other.time && active == other.active && repeat == other.repeat
  }
}