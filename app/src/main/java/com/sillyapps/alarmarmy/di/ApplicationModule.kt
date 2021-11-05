package com.sillyapps.alarmarmy.di

import android.content.Context
import com.sillyapps.alarmarmy.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideAlarmDao(db: AppDatabase) = db.alarmDao

}