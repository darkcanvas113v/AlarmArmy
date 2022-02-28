package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import com.sillyapps.common_models.alarm.alarm.Alarm

internal fun Alarm.toDomainModel(): EditorAlarm {
  return EditorAlarm(id, time, weekDays, repeat)
}

internal fun EditorAlarm.toCommonModel(): Alarm {
  return Alarm(id, time, true, weekDays, repeat)
}