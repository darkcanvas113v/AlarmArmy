package com.sillyapps.alarm_scheduler

import com.sillyapps.core_time.convertMillisToStringFormatWithDays
import com.sillyapps.core_time.convertToMillis
import com.sillyapps.core_time.getRemainingTime
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.ZonedDateTime

class RemainingTimeFunctionTest {

  @Test
  fun remainingTimeFunctionTest() {
    val zoneId = ZoneId.systemDefault()

    testInput(
      activeDays = com.sillyapps.core_time.AlarmConstants.tu + com.sillyapps.core_time.AlarmConstants.fr,
      time = 0L,
      currentTime = ZonedDateTime.of(LocalDateTime.of(2021, 12, 13, 0, 0, 0), zoneId).toEpochSecond(),
      answer = convertToMillis(24, 0, 0)
    )

    testInput(
      activeDays = com.sillyapps.core_time.AlarmConstants.mo + com.sillyapps.core_time.AlarmConstants.fr,
      time = 0L,
      currentTime = ZonedDateTime.of(LocalDateTime.of(2021, 12, 13, 1, 0, 0), zoneId).toEpochSecond(),
      answer = convertToMillis(4 * 24 - 1, 0, 0)
    )

    testInput(
      activeDays = com.sillyapps.core_time.AlarmConstants.mo,
      time = 0L,
      currentTime = ZonedDateTime.of(LocalDateTime.of(2021, 12, 14, 0, 0, 0), zoneId).toEpochSecond(),
      answer = convertToMillis(6 * 24, 0, 0)
    )

    testInput(
      activeDays = com.sillyapps.core_time.AlarmConstants.mo,
      time = 0L,
      currentTime = ZonedDateTime.of(LocalDateTime.of(2021, 12, 13, 0, 2, 0), zoneId).toEpochSecond(),
      answer = convertToMillis(6 * 24 + 23, 58, 0)
    )
  }

  private fun testInput(activeDays: Int, time: Long, currentTime: Long, answer: Long) {
    val output = getRemainingTime(activeDays, time, currentTime*1000L)
    println("Output of the function is ${convertMillisToStringFormatWithDays(output)}, answer is ${convertMillisToStringFormatWithDays(answer)}")
    assert(output == answer)
  }

}
