package com.sillyapps.alarm_ui.di

import android.content.Context
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import com.sillyapps.core_di.ScreenScope
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [AlarmRepository::class])
@ScreenScope
interface AlarmScreenComponent {

  fun getViewModel(): AlarmListViewModel

  @Component.Builder
  interface Builder {
    fun alarmRepository(repository: AlarmRepository): Builder
    fun build(): AlarmScreenComponent
  }
}