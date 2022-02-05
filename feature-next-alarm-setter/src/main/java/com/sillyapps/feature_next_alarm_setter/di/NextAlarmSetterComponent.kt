package com.sillyapps.feature_next_alarm_setter.di

import android.content.Context
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.core_di.FeatureScope
import com.sillyapps.core_di.modules.IOModule
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.data.NextAlarmRepositoryImpl
import com.sillyapps.feature_next_alarm_setter.domain.NextAlarmRepository
import com.sillyapps.feature_next_alarm_setter.service.NextAlarmSetterServiceImpl
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module

@Component(modules = [IOModule::class, RepositoryModule::class])
@FeatureScope
internal interface NextAlarmSetterComponent {

  fun inject(nextAlarmSetterService: NextAlarmSetterServiceImpl)

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

    fun build(): NextAlarmSetterComponent
  }

  companion object {
    @Volatile
    private var INSTANCE: NextAlarmSetterComponent? = null

    fun initialize(
      context: Context,
      alarmSetter: AlarmSetter,
      alarmRepository: AlarmRepository,
      currentAlarmRepository: CurrentAlarmRepository
    ) {
      synchronized(this) {
        INSTANCE = DaggerNextAlarmSetterComponent.builder()
          .context(context)
          .alarmSetter(alarmSetter)
          .alarmRepository(alarmRepository)
          .currentAlarmRepository(currentAlarmRepository)
          .build()
      }
    }

    fun resetAndGetInstance(): NextAlarmSetterComponent {
      synchronized(this) {
        val instance = INSTANCE ?: throw Error("NextAlarmSetterComponent is not initialized.")
        INSTANCE = null
        return instance
      }
    }
  }
}

@Module
interface RepositoryModule {
  @FeatureScope
  @Binds
  fun bindRepository(impl: NextAlarmRepositoryImpl): NextAlarmRepository
}
