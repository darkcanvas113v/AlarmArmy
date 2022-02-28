package com.sillyapps.alarm_scheduler

import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import org.junit.Test

class AlarmWatcherComparisonTest {

  @Test
  fun testWithDifferentTimes() {
    val first = AlarmWithRemainingTime(0, 1000, true, 0, false, 0, 0)
    val second = AlarmWithRemainingTime(0, 1010, true, 0, false, 0, 0)

    val output = first.isSameAs(second)
    
    assert(output == false)
  }

}