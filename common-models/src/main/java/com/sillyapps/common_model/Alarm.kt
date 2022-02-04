package com.sillyapps.common_model

data class Alarm(
  val id: Long,
  val time: Long,
  val active: Boolean,
  val repeat: Int
)