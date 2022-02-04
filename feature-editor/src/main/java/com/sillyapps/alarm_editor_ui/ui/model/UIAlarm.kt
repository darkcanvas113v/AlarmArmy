package com.sillyapps.alarm_editor_ui.ui.model

import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import com.sillyapps.core.*
import com.sillyapps.core_ui.int

data class UIAlarm(
  val id: Long = 0,
  val timeHours: Int = 0,
  val timeMinutes: Int = 0,

  val weekDays: WeekDays = WeekDays(),
  val repeat: Boolean = false
)

fun UIAlarm.timeInMillis(): Long {
  return convertToMillis(timeHours, timeMinutes)
}

data class WeekDays(
  val mo: Boolean = false,
  val tu: Boolean = false,
  val we: Boolean = false,
  val th: Boolean = false,
  val fr: Boolean = false,
  val sa: Boolean = false,
  val su: Boolean = false
)

fun WeekDays.toInt(): Int {
  com.sillyapps.core_time.AlarmConstants.let {
    return mo.int() * it.mo +
        tu.int() * it.tu +
        we.int() * it.we +
        th.int() * it.th +
        fr.int() * it.fr +
        sa.int() * it.sa +
        su.int() * it.su
  }
}

fun toWeekDays(weekDay: Int): WeekDays {
  com.sillyapps.core_time.AlarmConstants.apply {
    return WeekDays(
      (weekDay and mo != 0),
      (weekDay and tu != 0),
      (weekDay and we != 0),
      (weekDay and th != 0),
      (weekDay and fr != 0),
      (weekDay and sa != 0),
      (weekDay and su != 0))
  }
}

fun EditorAlarm.toUIModel(): UIAlarm {
  val hm = getHoursAndMinutes(time)
  return UIAlarm(
    id = id,
    timeHours = hm.first,
    timeMinutes = hm.second,
    weekDays = toWeekDays(weekDays),
    repeat = repeat
  )
}
