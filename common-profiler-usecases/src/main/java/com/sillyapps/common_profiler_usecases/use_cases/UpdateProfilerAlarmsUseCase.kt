package com.sillyapps.common_profiler_usecases.use_cases

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdateProfilerAlarmsUseCase @Inject constructor(
  private val repository: ProfilerRepository
) {

  suspend operator fun invoke(alarms: List<ProfilerAlarm>) {
    repository.updateProfilerAlarms(alarms)
  }

}