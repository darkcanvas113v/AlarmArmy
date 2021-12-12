package com.sillyapps.alarm_data.repository

import com.sillyapps.alarm_domain.alarm_editor.AlarmEditorDataSource
import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AlarmEditorDataSourceImpl @Inject constructor(): AlarmEditorDataSource {

  private val data: MutableStateFlow<Alarm> = MutableStateFlow(Alarm.INITIAL)

  override fun getAlarm(): Flow<Alarm> = data

  override fun update(alarm: Alarm) {
    data.value = alarm
  }

}