package com.sillyapps.alarm_scheduler.di

import android.content.Context
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.alarm_scheduler.service.AlarmWatcherService
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.core_di.modules.IOModule
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import dagger.*

@Component(modules = [IOModule::class])
@FeatureScope
internal interface AlarmWatcherComponent {

  fun inject(alarmWatcher: AlarmWatcherService)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun alarmSetter(alarmSetter: AlarmSetter): Builder

    @BindsInstance
    fun alarmRepository(repository: AlarmRepository): Builder

    @BindsInstance
    fun currentAlarmRepository(repository: CurrentAlarmRepository): Builder

    @BindsInstance
    fun profilerRepository(repository: ProfilerRepository): Builder

    fun build(): AlarmWatcherComponent
  }

  companion object {
    @Volatile
    private var INSTANCE: AlarmWatcherComponent? = null

    fun initialize(
      context: Context,
      alarmSetter: AlarmSetter,
      alarmRepository: AlarmRepository,
      currentAlarmRepository: CurrentAlarmRepository,
      profilerRepository: ProfilerRepository
    ) {
      synchronized(this) {
        INSTANCE = DaggerAlarmWatcherComponent.builder()
          .context(context)
          .alarmSetter(alarmSetter)
          .alarmRepository(alarmRepository)
          .currentAlarmRepository(currentAlarmRepository)
          .profilerRepository(profilerRepository)
          .build()
      }
    }

    fun resetAndGetInstance(): AlarmWatcherComponent {
      synchronized(this) {
        val instance = INSTANCE ?: throw Error("AlarmSchedulerComponent is not initialized.")
        INSTANCE = null
        return instance
      }
    }
  }
}