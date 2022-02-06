package com.sillyapps.alarm_editor_ui.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.alarm_editor_ui.di.AlarmID
import com.sillyapps.alarm_editor_ui.domain.AlarmEditorInteractor
import com.sillyapps.alarm_editor_ui.ui.model.toUIModel
import com.sillyapps.core_time.convertToMillis
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmEditorViewModel @Inject constructor(
  private val interactor: AlarmEditorInteractor,
  @AlarmID private val alarmID: Long
): ViewModel(), AlarmEditorStateHolder {

  override val alarm = interactor.getAlarm().map { it.toUIModel() }

  init {
    viewModelScope.launch {
      interactor.loadAlarm(alarmID)
    }
  }

  override fun enableAllDays() {
    viewModelScope.launch { interactor.enableAllDays() }
  }

  override fun disableAllDays() {
    viewModelScope.launch { interactor.disableAllDays() }
  }

  override fun toggleDay(day: Int) {
    viewModelScope.launch { interactor.toggleRepeatDay(day) }
  }

  override fun save() {
    viewModelScope.launch { interactor.saveAlarm() }
  }

  override fun hoursChanged(hours: Int) {
    viewModelScope.launch {
      val minutes = alarm.first().timeMinutes
      interactor.updateTime(convertToMillis(hours, minutes))
    }
  }

  override fun minutesChanged(minutes: Int) {
    viewModelScope.launch {
      val hours = alarm.first().timeHours
      interactor.updateTime(convertToMillis(hours, minutes))
    }
  }

}