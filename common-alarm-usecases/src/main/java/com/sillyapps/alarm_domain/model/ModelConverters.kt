package com.sillyapps.alarm_domain.model

import com.sillyapps.core_time.getRemainingTime

fun Alarm.calculateRemainingTime(from: Long = System.currentTimeMillis()): AlarmWithRemainingTime {
  val remainingTime = getRemainingTime(weekDays, time, from)
  return AlarmWithRemainingTime(
    id = id,
    time = time,
    active = active,
    weekDays = weekDays,
    remainingTime = remainingTime,
    repeat = repeat,
    startupTime = from + remainingTime
  )
}

fun AlarmWithRemainingTime.toAlarm(
  overrideActive: Boolean? = null
): Alarm {
  return Alarm(
    id = id,
    time = time,
    active = overrideActive ?: active,
    weekDays = weekDays,
    repeat = repeat
  )
}