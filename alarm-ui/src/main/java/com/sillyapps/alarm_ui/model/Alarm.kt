package com.sillyapps.alarm_ui.model

data class Alarm(
  val id: Long,
  val time: String,
  val active: Boolean,
  val repeat: String
)
