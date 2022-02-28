package com.sillyapps.alarmarmy.di

import android.content.Context
import com.sillyapps.alarmarmy.database.AppDatabase
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Component(modules = [DatabaseModule::class])
@AppScope
interface AppComponent {

  val db: AppDatabase

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AppComponent
  }

}

@Module
class DatabaseModule {
  @AppScope
  @Provides
  fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
}