package com.sillyapps.alarm_domain

import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AlarmEditorInteractor @Inject constructor(
  private val repository: AlarmRepository
) {

  suspend fun getAlarm(id: Long): Flow<Alarm> = repository.getAlarm(id)

  suspend fun saveAlarm(alarm: Alarm) = repository.saveAlarm(alarm)

}