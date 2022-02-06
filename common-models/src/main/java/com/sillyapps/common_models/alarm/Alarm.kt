package com.sillyapps.common_models.alarm

data class Alarm(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val weekDays: Int,
  val repeat: Boolean
)