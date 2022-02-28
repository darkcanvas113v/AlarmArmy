package com.sillyapps.feature_profiler.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm
import com.sillyapps.feature_profiler.ui.model.toCommonModel
import com.sillyapps.feature_profiler.ui.model.toProfilerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfilerScreenViewModel @Inject constructor(
  private val profilerRepository: ProfilerRepository
): ViewModel(), ProfilerScreenStateHolder {

  private val alarms = profilerRepository.getProfilerAlarms().map { alarms -> alarms.map { it.toProfilerModel() } }

  override fun getProfilerAlarms(): Flow<List<UIProfilerAlarm>> {
    return alarms
  }

  override fun deleteProfilerAlarm(profilerAlarm: UIProfilerAlarm) {
    viewModelScope.launch { profilerRepository.deleteProfilerAlarm(profilerAlarm.toCommonModel()) }
  }

  override fun updateProfilerAlarm(profilerAlarm: UIProfilerAlarm) {
    if (profilerAlarm.offset == 0L) return
    viewModelScope.launch { profilerRepository.upsertProfilerAlarm(profilerAlarm.toCommonModel()) }
  }
}