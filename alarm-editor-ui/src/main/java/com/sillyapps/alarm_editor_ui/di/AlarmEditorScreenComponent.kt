package com.sillyapps.alarm_editor_ui.di

import android.content.Context
import com.sillyapps.alarm_domain.alarm_editor.AlarmEditorDataSource
import com.sillyapps.alarm_domain.alarm_list.AlarmRepository
import com.sillyapps.alarm_editor_ui.screen.AlarmEditorViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Qualifier
import javax.inject.Scope

@Component()
@AlarmEditorScreenScope
interface AlarmEditorScreenComponent {
  fun getViewModel(): AlarmEditorViewModel

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    @BindsInstance
    fun alarmID(@AlarmID id: Long): Builder

    @BindsInstance
    fun alarmDataSource(alarmDataSource: AlarmEditorDataSource): Builder

    @BindsInstance
    fun repository(repository: AlarmRepository): Builder

    fun build(): AlarmEditorScreenComponent
  }
}

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AlarmEditorScreenScope

@Qualifier
annotation class AlarmID