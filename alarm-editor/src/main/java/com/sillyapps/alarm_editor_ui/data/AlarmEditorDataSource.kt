package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmEditorDataSource {
  fun update(alarm: EditorAlarm)

  fun getAlarm(): Flow<EditorAlarm>
}