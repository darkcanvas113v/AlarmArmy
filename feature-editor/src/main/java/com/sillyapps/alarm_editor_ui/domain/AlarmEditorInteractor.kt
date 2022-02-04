package com.sillyapps.alarm_editor_ui.domain

import com.sillyapps.core.AlarmConstants
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AlarmEditorInteractor @Inject constructor(
  private val repository: AlarmEditorRepository
) {

  private val data = repository.getAlarm()

  fun getAlarm() = repository.getAlarm()

  suspend fun loadAlarm(id: Long) {
    repository.loadAlarm(id)
  }

  suspend fun saveAlarm() = repository.saveAlarm()

  suspend fun enableAllDays() {
    val alarm = data.first()
    repository.update(alarm.copy(repeat = AlarmConstants.everyDay))
  }

  suspend fun disableAllDays() {
    val alarm = data.first()
    repository.update(alarm.copy(repeat = AlarmConstants.onlyOnce))
  }

  suspend fun toggleRepeatDay(day: Int) {
    val alarm = data.first()
    repository.update(alarm.copy(repeat = alarm.repeat xor day))
  }

  suspend fun updateTime(time: Long) {
    val alarm = data.first()
    repository.update(alarm.copy(time = time))
  }

}