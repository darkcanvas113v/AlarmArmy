package com.sillyapps.alarm_ui.di

import android.content.Context
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component()
@AlarmScreenScope
interface AlarmScreenComponent {

  fun getViewModel(): AlarmListViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun repository(repository: AlarmRepository): Builder

    fun build(): AlarmScreenComponent
  }

}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmScreenScope