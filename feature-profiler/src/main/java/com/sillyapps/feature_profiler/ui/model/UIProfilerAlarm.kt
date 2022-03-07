package com.sillyapps.feature_profiler.ui.model

import androidx.compose.ui.graphics.Color
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.core_time.convertMillisToStringFormatWithSign
import com.sillyapps.core_ui.compose.theme.Blue
import com.sillyapps.core_ui.compose.theme.Red

data class UIProfilerAlarm(
  val id: Long,
  val offset: Long,
  val text: String,
  val color: Color
) {

  companion object {
    val DEFAULT = UIProfilerAlarm(0, 0, "", Color.Black)
  }
}

fun ProfilerOffset.toProfilerModel(): UIProfilerAlarm {
  return UIProfilerAlarm(
    id = id,
    offset = offset,
    text = convertMillisToStringFormatWithSign(offset),
    color = when {
      offset < 0L -> Blue
      else -> Red
    }
  )
}

fun UIProfilerAlarm.toCommonModel(): ProfilerOffset {
  return ProfilerOffset(id, offset)
}