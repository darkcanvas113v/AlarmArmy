package com.sillyapps.alarm_data.di

import android.content.Context
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_data.persistence.AlarmDatabase
import com.sillyapps.alarm_data.repository.AlarmRepositoryImpl
import com.sillyapps.alarm_domain.AlarmRepository
import dagger.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Scope

@Component(modules = [AlarmModule::class, RepositoryModule::class])
@AlarmScope
interface AlarmComponent {

  val repository: AlarmRepository

  fun getAlarmDao(): AlarmDao

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AlarmComponent
  }

}

@Module
class AlarmModule {
  @AlarmScope
  @Provides
  fun provideDatabase(context: Context) = AlarmDatabase.getInstance(context)

  @AlarmScope
  @Provides
  fun provideAlarmDao(db: AlarmDatabase) = db.alarmDao

  @AlarmScope
  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}

@Module
interface RepositoryModule {
  @AlarmScope
  @Binds
  fun bindAlarmRepository(repository: AlarmRepositoryImpl): AlarmRepository

}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmScope

@Qualifier
annotation class IODispatcher