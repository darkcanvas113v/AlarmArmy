package com.sillyapps.alarm_domain.alarm_editor

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_domain.alarm_list.AlarmRepository
import com.sillyapps.core.AlarmConstants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AlarmEditorInteractor @Inject constructor(
  private val repository: AlarmRepository,
  private val alarmEditorDataSource: AlarmEditorDataSource
) {

  suspend fun loadAlarm(id: Long) {
    if (id == 0L) return

    alarmEditorDataSource.update(repository.getAlarm(id).first())
  }

  fun getAlarm(): Flow<Alarm> = alarmEditorDataSource.getAlarm()

  suspend fun saveAlarm() {
    val alarm = alarmEditorDataSource.getAlarm().first()
    repository.upsert(alarm)
  }

  suspend fun enableAllDays() {
    val alarm = alarmEditorDataSource.getAlarm().first()
    alarmEditorDataSource.update(alarm.copy(repeat = AlarmConstants.everyDay))
  }

  suspend fun disableAllDays() {
    val alarm = alarmEditorDataSource.getAlarm().first()
    alarmEditorDataSource.update(alarm.copy(repeat = AlarmConstants.onlyOnce))
  }

  suspend fun toggleRepeatDay(day: Int) {
    val alarm = alarmEditorDataSource.getAlarm().first()
    alarmEditorDataSource.update(alarm.copy(repeat = alarm.repeat xor day))
  }

  suspend fun updateTime(time: Long) {
    val alarm = alarmEditorDataSource.getAlarm().first()
    alarmEditorDataSource.update(alarm.copy(time = time))
  }

}