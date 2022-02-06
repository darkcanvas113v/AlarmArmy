package com.sillyapps.alarm_ui.model

import com.sillyapps.common_models.alarm.Alarm
import com.sillyapps.core_time.convertMillisToStringFormat


data class UIAlarm(
  val id: Long,
  val time: String,
  val active: Boolean,
  val weekDays: String,
  val repeat: Boolean
)

fun weekDaysToString(repeat: Int): String {
  if (repeat == com.sillyapps.core_time.AlarmConstants.everyDay) return "Every day"
  if (repeat == com.sillyapps.core_time.AlarmConstants.onlyOnce) return "Only once"

  val str = StringBuilder()
  if (repeat and com.sillyapps.core_time.AlarmConstants.mo != 0) str.append("Mo ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.tu != 0) str.append("Tu ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.we != 0) str.append("We ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.th != 0) str.append("Th ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.fr != 0) str.append("Fr ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.sa != 0) str.append("Sa ")
  if (repeat and com.sillyapps.core_time.AlarmConstants.su != 0) str.append("Su ")

  if (str.isBlank()) throw Exception("Invalid repeat value: $repeat")

  return str.dropLast(1).toString()
}

fun Alarm.toUIModel(): UIAlarm {
  return UIAlarm(id, convertMillisToStringFormat(time), active, weekDaysToString(weekDays), repeat)
}
