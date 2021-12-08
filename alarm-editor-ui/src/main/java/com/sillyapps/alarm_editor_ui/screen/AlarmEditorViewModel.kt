package com.sillyapps.alarm_editor_ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.alarm_domain.AlarmEditorInteractor
import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_editor_ui.di.AlarmID
import com.sillyapps.alarm_editor_ui.model.UIAlarm
import com.sillyapps.alarm_editor_ui.model.toUIModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmEditorViewModel @Inject constructor(
  private val alarmEditorInteractor: AlarmEditorInteractor,
  @AlarmID private val alarmID: Long
): ViewModel(), AlarmEditorStateHolder {

  private var _alarm: UIAlarm by mutableStateOf(UIAlarm())
  override val alarm = _alarm

  init {
    if (alarmID != 0L)
      viewModelScope.launch {
        val result = alarmEditorInteractor.getAlarm(alarmID)
        _alarm = result.first().toUIModel()
      }
  }

}