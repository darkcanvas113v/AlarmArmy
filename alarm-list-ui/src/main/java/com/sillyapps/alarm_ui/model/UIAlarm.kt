package com.sillyapps.alarm_ui.model

import com.sillyapps.alarm_domain.model.Alarm


data class UIAlarm(
  val id: Long,
  val time: String,
  val active: Boolean,
  val repeat: String
)

fun Alarm.toUIModel(): UIAlarm {
  return UIAlarm(id, time.toString(), active, repeat.toString())
}
