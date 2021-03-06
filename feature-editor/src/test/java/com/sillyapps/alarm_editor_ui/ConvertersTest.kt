package com.sillyapps.alarm_editor_ui

import com.sillyapps.alarm_editor_ui.ui.model.WeekDays
import com.sillyapps.alarm_editor_ui.ui.model.toInt
import com.sillyapps.alarm_editor_ui.ui.model.toWeekDays
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ConvertersTest {

  @Test
  fun repeatConversionToInt_isWorkingProperly() {
    val repeat = WeekDays(
      mo = true,
      tu = true,
      we = true,
      th = true,
      fr = true,
      sa = true,
      su = true
    )

    val repeatConvertedToInt = repeat.toInt()

    assertEquals(repeatConvertedToInt, 0b01111111)
  }

  @Test
  fun intConversionToRepeat_isWorkingProperly() {
    val actualRepeat = WeekDays(
      mo = true,
      tu = true,
      we = true,
      th = true,
      fr = true,
      sa = true,
      su = true
    )

    val intConvertedToRepeat = toWeekDays(0b01111111)

    assertEquals(intConvertedToRepeat, actualRepeat)
  }
}