package com.sillyapps.alarm_scheduler

import com.sillyapps.alarm_scheduler.domain.AlarmScheduler
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import org.junit.Test

class AlarmSchedulerComparisonTest {

  @Test
  fun testWithDifferentTimes() {
    val first = SchedulerAlarm(0, 1000, true, 0, 0)
    val second = SchedulerAlarm(0, 1010, true, 0, 0)

    val output = first.isSameAs(second)
    
    assert(output == false)
  }

}