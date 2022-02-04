package com.sillyapps.alarm_editor_ui.domain

import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import kotlinx.coroutines.flow.Flow

interface AlarmEditorRepository {

  fun getAlarm(): Flow<EditorAlarm>

  suspend fun loadAlarm(id: Long)

  suspend fun saveAlarm()

  suspend fun update(alarm: EditorAlarm)

}