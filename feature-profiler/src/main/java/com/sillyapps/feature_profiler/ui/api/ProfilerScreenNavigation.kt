package com.sillyapps.feature_profiler.ui.api

import androidx.compose.runtime.Composable
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_ui.daggerViewModel
import com.sillyapps.feature_profiler.ui.di.DaggerProfilerScreenComponent
import com.sillyapps.feature_profiler.ui.screen.ProfilerScreen
import com.sillyapps.feature_profiler.ui.screen.ProfilerScreenViewModel

@Composable
fun ProfilerScreenNavigation(
  profilerRepository: ProfilerRepository,

) {
  val screenComponent = DaggerProfilerScreenComponent.builder()
    .repository(profilerRepository)
    .build()

  val viewModel: ProfilerScreenViewModel = daggerViewModel {
    screenComponent.getViewModel()
  }

  ProfilerScreen(
    profilerScreenStateHolder = viewModel
  )
}