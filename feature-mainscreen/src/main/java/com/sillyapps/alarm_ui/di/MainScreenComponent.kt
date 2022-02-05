package com.sillyapps.alarm_ui.di

import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_ui.ui.AlarmListViewModel
import com.sillyapps.core_di.ScreenScope
import dagger.BindsInstance
import dagger.Component

@Component()
@ScreenScope
interface MainScreenComponent {

  fun getViewModel(): AlarmListViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun alarmRepository(repository: AlarmRepository): Builder
    fun build(): MainScreenComponent
  }
}