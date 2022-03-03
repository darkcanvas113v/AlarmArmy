package com.sillyapps.feature_profiler.ui.di

import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.ScreenScope
import com.sillyapps.feature_profiler.ui.screen.ProfilerScreenViewModel
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@ScreenScope
@Component
interface ProfilerScreenComponent {

  fun getViewModel(): ProfilerScreenViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun repository(repository: ProfilerRepository): Builder
    fun build(): ProfilerScreenComponent
  }

}