package com.sillyapps.alarmarmy.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Component(modules = [AppModule::class])
@AppScope
interface AppComponent {

  val context: Context

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AppComponent
  }

}

@Module
class AppModule {

}