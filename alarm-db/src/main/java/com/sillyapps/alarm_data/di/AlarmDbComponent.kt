package com.sillyapps.alarm_data.di

import android.content.Context
import android.content.SharedPreferences
import com.sillyapps.alarm_data.common_alarm.AlarmRepositoryImpl
import com.sillyapps.alarm_data.current_alarm.CurrentAlarmRepositoryImpl
import com.sillyapps.alarm_data.persistence.AlarmDatabase
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IOModule
import dagger.*
import kotlinx.coroutines.CoroutineScope

@Component(modules = [DbModule::class, RepositoryModule::class, IOModule::class])
@AppScope
interface AlarmDbComponent {

  val alarmRepository: AlarmRepository
  val currentAlarmRepository: CurrentAlarmRepository

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun coroutineScope(@IOCoroutineScope coroutineScope: CoroutineScope): Builder

    @BindsInstance
    fun database(database: AlarmDatabase): Builder

    @BindsInstance
    fun sharedPref(sharedPreferences: SharedPreferences): Builder

    fun build(): AlarmDbComponent
  }

}

@Module
class DbModule {
  @AppScope
  @Provides
  fun provideAlarmDao(db: AlarmDatabase) = db.provideAlarmDao()
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
