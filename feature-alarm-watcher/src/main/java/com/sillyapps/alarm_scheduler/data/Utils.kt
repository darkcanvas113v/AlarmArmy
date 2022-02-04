package com.sillyapps.alarm_scheduler.data

import com.sillyapps.core.AlarmConstants
import com.sillyapps.core.convertToMillis
import java.time.DayOfWeek
import java.time.LocalDateTime

fun getRemainingTime(activeDays: Int, time: Long, currentTime: LocalDateTime = LocalDateTime.now()): Long {
  val now = currentTime.toMillisAfterStartOfTheDay()

  if (activeDays == AlarmConstants.onlyOnce) {
    if (time > now) {
      return time - now
    }
    return time - now + AlarmConstants.ONE_DAY_DURATION
  }

  var day = getDayMask(currentTime.dayOfWeek)
  var dayCount = 0

  // Если будильник поставлен на сегодня, но время уже прошло
  if (day and activeDays != 0 && time <= now) {
    day = day shl 1
    dayCount++

    if (day == AlarmConstants.outOfTheWeek)
      day = AlarmConstants.mo
  }

  while (day and activeDays == 0) {
    day = day shl 1
    dayCount++

    if (day == AlarmConstants.outOfTheWeek)
      day = AlarmConstants.mo
  }

  return time - now + dayCount * AlarmConstants.ONE_DAY_DURATION
}

fun getDayMask(day: DayOfWeek): Int {
  return when(day) {
    DayOfWeek.MONDAY -> AlarmConstants.mo
    DayOfWeek.TUESDAY -> AlarmConstants.tu
    DayOfWeek.WEDNESDAY -> AlarmConstants.we
    DayOfWeek.THURSDAY -> AlarmConstants.th
    DayOfWeek.FRIDAY -> AlarmConstants.fr
    DayOfWeek.SATURDAY -> AlarmConstants.sa
    DayOfWeek.SUNDAY -> AlarmConstants.su
  }
}

fun LocalDateTime.toMillisAfterStartOfTheDay(): Long {
  return convertToMillis(hour, minute, second)
}