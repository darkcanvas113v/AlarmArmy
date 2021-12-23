package com.sillyapps.alarm_scheduler.di

import android.content.Context
import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_scheduler.data.AlarmSchedulerRepositoryImpl
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerRepository
import com.sillyapps.alarm_scheduler.service.AlarmSchedulerServiceImpl
import dagger.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmSchedulerScope

@Component(modules = [RepositoryModule::class, IOModule::class])
@AlarmSchedulerScope
interface AlarmSchedulerComponent {

  fun inject(alarmScheduler: AlarmSchedulerServiceImpl)

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun alarmDao(alarmDao: AlarmDao): Builder

    fun build(): AlarmSchedulerComponent
  }
}

@Module
interface RepositoryModule {
  @AlarmSchedulerScope
  @Binds
  fun bindRepository(impl: AlarmSchedulerRepositoryImpl): AlarmSchedulerRepository
}

@Module
class IOModule {
  @AlarmSchedulerScope
  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}