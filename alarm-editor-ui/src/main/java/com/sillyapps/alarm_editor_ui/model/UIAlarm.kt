package com.sillyapps.alarm_editor_ui.model

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.core.formatIfNeeded
import com.sillyapps.core.formatValue
import com.sillyapps.core.getHoursAndMinutes

data class UIAlarm(
  val id: Long = 0,
  var timeHours: Int = 0,
  var timeMinutes: Int = 0,
  var repeat: Int = 0
)

fun Alarm.toUIModel(): UIAlarm {
  val hm = getHoursAndMinutes(time)
  return UIAlarm(
    id,
    hm.first,
    hm.second,
    repeat)
}
