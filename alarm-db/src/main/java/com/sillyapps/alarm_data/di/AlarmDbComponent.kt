package com.sillyapps.alarm_data.di

import android.content.Context
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_data.persistence.AlarmDatabase
import com.sillyapps.alarm_data.common_alarm.AlarmRepositoryImpl
import com.sillyapps.alarm_data.current_alarm.CurrentAlarmRepositoryImpl
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.modules.IOModule
import dagger.*

@Component(modules = [AlarmModule::class, RepositoryModule::class, IOModule::class])
@AppScope
interface AlarmDbComponent {

  val alarmRepository: AlarmRepository
  val currentAlarmRepository: CurrentAlarmRepository

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AlarmDbComponent
  }

}

@Module
class AlarmModule {
  @AppScope
  @Provides
  fun provideDatabase(context: Context) = AlarmDatabase.getInstance(context)

  @AppScope
  @Provides
  fun provideAlarmDao(db: AlarmDatabase) = db.alarmDao
}

@Module
interface RepositoryModule {
  @AppScope
  @Binds
  fun bindAlarmRepository(repository: AlarmRepositoryImpl): AlarmRepository

  @AppScope
  @Binds
  fun bindCurrentAlarmRepository(currentRepository: CurrentAlarmRepositoryImpl): CurrentAlarmRepository
}