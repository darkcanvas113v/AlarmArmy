package com.sillyapps.alarmarmy.di

import android.content.Context
import android.content.SharedPreferences
import com.sillyapps.alarmarmy.data.AppDatabase
import com.sillyapps.alarmarmy.data.SharedPref
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Scope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@Component(modules = [DatabaseModule::class, SharedPrefModule::class])
@AppScope
interface AppComponent {

  val db: AppDatabase

  val sharedPref: SharedPreferences

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun context(context: Context): Builder

    fun build(): AppComponent
  }

}

@Module
class SharedPrefModule {
  @AppScope
  @Provides
  fun provideSharedPref(context: Context): SharedPreferences {
    return context.getSharedPreferences(
      SharedPref.TAG,
      Context.MODE_PRIVATE
    )
  }
}

@Module
class DatabaseModule {
  @AppScope
  @Provides
  fun provideDatabase(context: Context) = AppDatabase.getInstance(context)
}