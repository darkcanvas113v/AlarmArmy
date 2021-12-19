package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AlarmEditorDataSourceImpl @Inject constructor(): AlarmEditorDataSource {

  private val data: MutableStateFlow<EditorAlarm> = MutableStateFlow(EditorAlarm.INITIAL)

  override fun getAlarm(): Flow<EditorAlarm> = data

  override fun update(alarm: EditorAlarm) {
    data.value = alarm
  }

}