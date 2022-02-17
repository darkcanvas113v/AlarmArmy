package com.sillyapps.feature_profiler.ui.model

import androidx.compose.ui.graphics.Color
import com.sillyapps.core_time.convertMillisToStringFormatWithSign
import com.sillyapps.core_ui.theme.Blue
import com.sillyapps.core_ui.theme.Green500
import com.sillyapps.core_ui.theme.Red

data class UIProfilerAlarm(
  val offset: Long,
  val text: String,
  val color: Color
)

fun convertToProfilerUIModel(offset: Long): UIProfilerAlarm {
  return UIProfilerAlarm(
    offset = offset,
    text = convertMillisToStringFormatWithSign(offset),
    color = when {
      offset < 0L -> Blue
      else -> Red
    }
  )
}