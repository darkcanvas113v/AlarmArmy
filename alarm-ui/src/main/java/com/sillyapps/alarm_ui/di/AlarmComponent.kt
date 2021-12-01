package com.sillyapps.alarm_ui.di

import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import dagger.Component
import javax.inject.Scope

@Component(dependencies = [AlarmDeps::class])
@AlarmScreenScope
interface AlarmComponent {

  fun getViewModel(): AlarmListViewModel

  @Component.Builder
  interface Builder {
    fun deps(deps: AlarmDeps): Builder
    fun build(): AlarmComponent
  }

}

interface AlarmDeps {

}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmScreenScope