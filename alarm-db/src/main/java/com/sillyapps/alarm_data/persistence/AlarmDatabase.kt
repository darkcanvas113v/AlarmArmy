package com.sillyapps.alarm_data.persistence

interface AlarmDatabase {
  fun provideAlarmDao(): AlarmDao
}