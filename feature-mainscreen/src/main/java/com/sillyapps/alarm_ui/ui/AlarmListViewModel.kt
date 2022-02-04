package com.sillyapps.alarm_ui.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.alarm_domain.use_cases.GetAlarmsUseCase
import com.sillyapps.alarm_ui.domain.ToggleAlarmUseCase
import com.sillyapps.alarm_ui.model.UIAlarm
import com.sillyapps.alarm_ui.model.toUIModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class AlarmListViewModel @Inject constructor(
  private val getAlarmsUseCase: GetAlarmsUseCase,
  private val toggleAlarmUseCase: ToggleAlarmUseCase
): ViewModel(), AlarmStateHolder {

  override fun getAlarms(): Flow<List<UIAlarm>> = getAlarmsUseCase().map { alarmList ->
    alarmList.map { it.toUIModel() }
  }

  override fun toggleAlarm(alarmId: Long) {
    viewModelScope.launch {
      toggleAlarmUseCase(alarmId)
    }
  }

}