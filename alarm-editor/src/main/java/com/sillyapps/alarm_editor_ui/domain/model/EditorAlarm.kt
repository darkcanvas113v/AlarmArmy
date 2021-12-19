package com.sillyapps.alarm_editor_ui.domain.model

data class EditorAlarm(
  val id: Long,
  val time: Long,
  val repeat: Int
) {
  companion object {
    val INITIAL = EditorAlarm(0L, 0L, 0)
  }
}