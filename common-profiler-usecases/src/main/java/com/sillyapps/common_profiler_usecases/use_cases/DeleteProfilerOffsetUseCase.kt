package com.sillyapps.common_profiler_usecases.use_cases

import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import javax.inject.Inject

class DeleteProfilerOffsetUseCase @Inject constructor(
  private val repository: ProfilerRepository
) {

  suspend operator fun invoke(profilerOffset: ProfilerOffset) {
    repository.deleteProfilerOffset(profilerOffset)
  }

}