package com.sillyapps.alarm_ui.model

import com.sillyapps.common_model.Alarm
import com.sillyapps.core.AlarmConstants
import com.sillyapps.core.convertMillisToStringFormat


data class UIAlarm(
  val id: Long,
  val time: String,
  val active: Boolean,
  val repeat: String
)

fun repeatToString(repeat: Int): String {
  if (repeat == AlarmConstants.everyDay) return "Every day"
  if (repeat == AlarmConstants.onlyOnce) return "Only once"

  val str = StringBuilder()
  if (repeat and AlarmConstants.mo != 0) str.append("Mo ")
  if (repeat and AlarmConstants.tu != 0) str.append("Tu ")
  if (repeat and AlarmConstants.we != 0) str.append("We ")
  if (repeat and AlarmConstants.th != 0) str.append("Th ")
  if (repeat and AlarmConstants.fr != 0) str.append("Fr ")
  if (repeat and AlarmConstants.sa != 0) str.append("Sa ")
  if (repeat and AlarmConstants.su != 0) str.append("Su ")

  if (str.isBlank()) throw Exception("Invalid repeat value: $repeat")

  return str.dropLast(1).toString()
}

fun Alarm.toUIModel(): UIAlarm {
  return UIAlarm(id, convertMillisToStringFormat(time), active, repeatToString(repeat))
}
