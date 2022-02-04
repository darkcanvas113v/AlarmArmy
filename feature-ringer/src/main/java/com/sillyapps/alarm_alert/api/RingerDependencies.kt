package com.sillyapps.alarm_alert.api

interface RingerDependencies {
  fun provideNextAlarmSetter(): NextAlarmSetter
}