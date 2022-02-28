package com.sillyapps.profiler_db.di

import android.content.Context
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.core_di.AppScope
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.profiler_db.persistence.ProfilerDatabase
import com.sillyapps.profiler_db.repositories.ProfilerRepositoryImpl
import dagger.*
import kotlinx.coroutines.CoroutineScope

@Component(modules = [DatabaseModule::class, RepositoryModule::class])
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

    fun build(): ProfilerDbComponent
  }
}

@Module
interface RepositoryModule {
  @AppScope
  @Binds
  fun bindRepository(repository: ProfilerRepositoryImpl): ProfilerRepository
}

@Module
object DatabaseModule {
  @AppScope
  @Provides
  fun provideProfilerDao(db: ProfilerDatabase) = db.provideProfilerDao()
}