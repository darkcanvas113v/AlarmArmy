package com.sillyapps.common_profiler_usecases.use_cases

import com.sillyapps.common_models.alarm.profiler.ProfilerAlarm
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProfilerAlarmsUseCase @Inject constructor(
  private val repository: ProfilerRepository
) {

  operator fun invoke(): Flow<List<ProfilerAlarm>> {
    return repository.getProfilerAlarms()
  }

}