package com.sillyapps.alarm_scheduler

import com.sillyapps.alarm_scheduler.data.getRemainingTime
import com.sillyapps.core.AlarmConstants
import com.sillyapps.core.convertMillisToStringFormat
import com.sillyapps.core.convertMillisToStringFormatWithDays
import com.sillyapps.core.convertToMillis
import org.junit.Test
import java.time.LocalDateTime

class RemainingTimeFunctionTest {

  @Test
  fun remainingTimeFunctionTest() {
    testInput(
      activeDays = AlarmConstants.tu + AlarmConstants.fr,
      time = 0L,
      currentTime = LocalDateTime.of(2021, 12, 13, 0, 0, 0),
      answer = convertToMillis(24, 0, 0)
    )

    testInput(
      activeDays = AlarmConstants.mo + AlarmConstants.fr,
      time = 0L,
      currentTime = LocalDateTime.of(2021, 12, 13, 1, 0, 0),
      answer = convertToMillis(4 * 24 - 1, 0, 0)
    )

    testInput(
      activeDays = AlarmConstants.mo,
      time = 0L,
      currentTime = LocalDateTime.of(2021, 12, 14, 0, 0, 0),
      answer = convertToMillis(6 * 24, 0, 0)
    )

    testInput(
      activeDays = AlarmConstants.mo,
      time = 0L,
      currentTime = LocalDateTime.of(2021, 12, 13, 0, 2, 0),
      answer = convertToMillis(6 * 24 + 23, 58, 0)
    )
  }

  private fun testInput(activeDays: Int, time: Long, currentTime: LocalDateTime, answer: Long) {
    val output = getRemainingTime(activeDays, time, currentTime)
    println("Output of the function is ${convertMillisToStringFormatWithDays(output)}, answer is ${convertMillisToStringFormatWithDays(answer)}")
    assert(output == answer)
  }

}
