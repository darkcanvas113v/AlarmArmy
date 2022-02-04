package com.sillyapps.alarm_editor_ui.di

import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_editor_ui.data.AlarmEditorDataSource
import com.sillyapps.alarm_editor_ui.data.AlarmEditorDataSourceImpl
import com.sillyapps.alarm_editor_ui.data.AlarmEditorRepositoryImpl
import com.sillyapps.alarm_editor_ui.domain.AlarmEditorRepository
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorViewModel
import com.sillyapps.core_di.modules.IOModule
import dagger.*
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
    fun alarmID(@AlarmID id: Long): Builder

    @BindsInstance
    fun repository(repository: AlarmRepository): Builder

    fun build(): AlarmEditorComponent
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