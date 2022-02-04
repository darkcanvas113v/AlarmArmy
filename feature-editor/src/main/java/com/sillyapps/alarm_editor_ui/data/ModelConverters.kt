package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm

internal fun AlarmDto.toDomainModel(): EditorAlarm {
  return EditorAlarm(id, time, repeat)
}

internal fun EditorAlarm.toDataModel(): AlarmDto {
  return AlarmDto(id, time, true, repeat)
}