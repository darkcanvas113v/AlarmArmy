package com.sillyapps.alarmarmy.di

import com.sillyapps.alarm_ui.di.AlarmDeps
import dagger.Component
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Component
@AppScope
interface AppComponent: AlarmDeps {

  @Component.Builder
  interface Builder {
    fun build(): AppComponent
  }

}
