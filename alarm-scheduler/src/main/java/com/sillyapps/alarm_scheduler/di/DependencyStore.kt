package com.sillyapps.alarm_scheduler.di

import android.content.Context
import com.sillyapps.alarm_data.persistence.AlarmDao
import java.lang.Error

interface Dependencies {
  fun provideContext(): Context
  fun provideAlarmDao(): AlarmDao

  companion object {
    private var deps: Dependencies? = null

    fun initialize(context: Context, alarmDao: AlarmDao) {
      deps = object : Dependencies {
        override fun provideContext() = context

        override fun provideAlarmDao(): AlarmDao = alarmDao
      }
    }

    fun getDeps(): Dependencies {
      return deps ?: throw Error("AlarmScheduler component dependencies were not initialized")
    }
  }
}