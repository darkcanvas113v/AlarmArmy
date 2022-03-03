package com.sillyapps.profiler_db.di

import android.content.Context
import android.content.SharedPreferences
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_di.modules.IOModule
import com.sillyapps.profiler_db.persistence.ProfilerDataSourceImpl
import com.sillyapps.profiler_db.persistence.ProfilerDatabase
import com.sillyapps.profiler_db.repositories.ProfilerDataSource
import com.sillyapps.profiler_db.repositories.ProfilerRepositoryImpl
import dagger.*
import kotlinx.coroutines.CoroutineScope

@Component(modules = [DatabaseModule::class, RepositoryModule::class, IOModule::class])
@AppScope
interface ProfilerDbComponent {

  val profilerRepository: ProfilerRepository

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun coroutineScope(@IOCoroutineScope coroutineScope: CoroutineScope): Builder

    @BindsInstance
    fun database(db: ProfilerDatabase): Builder

    @BindsInstance
    fun sharedPref(sharedPreferences: SharedPreferences): Builder

    fun build(): ProfilerDbComponent
  }
}

@Module
interface RepositoryModule {
  @AppScope
  @Binds
  fun bindRepository(repository: ProfilerRepositoryImpl): ProfilerRepository

  @AppScope
  @Binds
  fun bindProfilerStateDataSource(dataSource: ProfilerDataSourceImpl): ProfilerDataSource
}

@Module
object DatabaseModule {
  @AppScope
  @Provides
  fun provideProfilerDao(db: ProfilerDatabase) = db.provideProfilerDao()
}