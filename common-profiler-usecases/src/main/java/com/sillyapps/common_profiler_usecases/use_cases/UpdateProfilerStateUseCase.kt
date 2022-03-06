package com.sillyapps.common_profiler_usecases.use_cases

import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import javax.inject.Inject

class UpdateProfilerStateUseCase @Inject constructor(
  private val repository: ProfilerRepository
) {

  suspend operator fun invoke(state: ProfilerState) {
    repository.setProfilerState(state)
  }

}