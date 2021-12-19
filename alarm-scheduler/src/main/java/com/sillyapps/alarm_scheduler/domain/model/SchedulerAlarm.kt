package com.sillyapps.alarm_scheduler.domain.model

data class SchedulerAlarm(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val repeat: Int
)