package com.sillyapps.alarm_ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.alarm_domain.AlarmListInteractor
import com.sillyapps.alarm_ui.model.UIAlarm
import com.sillyapps.alarm_ui.model.toUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmListViewModel @Inject constructor(
  private val interactor: AlarmListInteractor
): ViewModel(), AlarmStateHolder {

  override fun getAlarms(): Flow<List<UIAlarm>> = interactor.getAlarms().map { alarmList ->
    alarmList.map { it.toUIModel() }
  }

  override fun toggleAlarm(alarmId: Long) {
    viewModelScope.launch {
      interactor.toggleAlarmActive(alarmId)
    }
  }

}