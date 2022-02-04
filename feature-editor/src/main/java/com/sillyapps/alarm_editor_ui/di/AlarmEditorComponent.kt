package com.sillyapps.alarm_editor_ui.di

import android.content.Context
import com.sillyapps.alarm_data.di.AlarmComponent
import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_editor_ui.data.AlarmEditorDataSource
import com.sillyapps.alarm_editor_ui.data.AlarmEditorDataSourceImpl
import com.sillyapps.alarm_editor_ui.data.AlarmEditorRepositoryImpl
import com.sillyapps.alarm_editor_ui.domain.AlarmEditorRepository
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorViewModel
import dagger.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmEditorScope

@Component(modules = [RepositoryModule::class, DataModule::class, IOModule::class])
@AlarmEditorScope
interface AlarmEditorComponent {
  fun getViewModel(): AlarmEditorViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun alarmID(@AlarmID id: Long): Builder

    @BindsInstance
    fun alarmDao(alarmDao: AlarmDao): Builder

    fun build(): AlarmEditorComponent
  }
}

@Module
class IOModule {
  @AlarmEditorScope
  @Provides
  @IODispatcher
  fun provideIODispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}

@Module
interface DataModule {
  @AlarmEditorScope
  @Binds
  fun bindAlarmEditorDataSource(dataSource: AlarmEditorDataSourceImpl): AlarmEditorDataSource
}

@Module
interface RepositoryModule {
  @AlarmEditorScope
  @Binds
  fun bindAlarmRepository(repository: AlarmEditorRepositoryImpl): AlarmEditorRepository
}

@Qualifier
annotation class AlarmID