package com.sillyapps.alarm_scheduler.di

import android.content.Context
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerRepository
import com.sillyapps.alarm_scheduler.service.AlarmScheduler
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component()
@AlarmSchedulerScreenScope
interface AlarmSchedulerComponent {

  fun inject(alarmScheduler: AlarmScheduler)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun alarmDataSource(alarmSchedulerRepository: AlarmSchedulerRepository): Builder

    @BindsInstance
    fun repository(repository: AlarmRepository): Builder

    fun build(): AlarmSchedulerComponent
  }
}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmSchedulerScreenScope