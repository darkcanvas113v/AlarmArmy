package com.sillyapps.alarm_domain.model

import com.sillyapps.core_time.getRemainingTime

internal fun Alarm.calculateRemainingTime(): AlarmWithRemainingTime {
  return AlarmWithRemainingTime(
    id = id,
    time = time,
    active = active,
    repeat = weekDays,
    remainingTime = getRemainingTime(weekDays, time)
  )
}