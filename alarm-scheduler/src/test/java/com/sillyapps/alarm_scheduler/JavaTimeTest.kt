package com.sillyapps.alarm_scheduler

import java.time.LocalDateTime

fun testJavaTime() {
  val now = LocalDateTime.now()
  println("Now is ${now.dayOfWeek}")
}
