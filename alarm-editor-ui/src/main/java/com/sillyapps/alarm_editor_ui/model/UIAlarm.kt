package com.sillyapps.alarm_editor_ui.model

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.core.*
import com.sillyapps.core_ui.int

data class UIAlarm(
  val id: Long = 0,
  val timeHours: Int = 0,
  val timeMinutes: Int = 0,

  val repeat: Repeat = Repeat()
)

fun UIAlarm.timeInMillis(): Long {
  return convertToMillis(timeHours, timeMinutes)
}

data class Repeat(
  val mo: Boolean = false,
  val tu: Boolean = false,
  val we: Boolean = false,
  val th: Boolean = false,
  val fr: Boolean = false,
  val sa: Boolean = false,
  val su: Boolean = false
)

fun Repeat.toInt(): Int {
  AlarmConstants.let {
    return mo.int() * it.mo +
        tu.int() * it.tu +
        we.int() * it.we +
        th.int() * it.th +
        fr.int() * it.fr +
        sa.int() * it.sa +
        su.int() * it.su
  }
}

fun toRepeat(repeat: Int): Repeat {
  AlarmConstants.apply {
    return Repeat(
      (repeat and mo != 0),
      (repeat and tu != 0),
      (repeat and we != 0),
      (repeat and th != 0),
      (repeat and fr != 0),
      (repeat and sa != 0),
      (repeat and su != 0))
  }
}

fun Alarm.toUIModel(): UIAlarm {
  val hm = getHoursAndMinutes(time)
  return UIAlarm(
    id = id,
    timeHours = hm.first,
    timeMinutes = hm.second,
    repeat = toRepeat(repeat))
}
