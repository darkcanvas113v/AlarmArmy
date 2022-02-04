package com.sillyapps.alarm_alert.di

import android.content.Context
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService
import dagger.BindsInstance
import dagger.Component

@Component
interface RingerComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun nextAlarmSetter(nextAlarmSetterService: NextAlarmSetterService): Builder

    fun build(): RingerComponent
  }

  companion object {
    @Volatile
    private var INSTANCE: RingerComponent? = null

    fun initialize(
      nextAlarmSetterService: NextAlarmSetterService
    ) {
      synchronized(this) {
        INSTANCE = DaggerRingerComponent.builder()
          .nextAlarmSetter(nextAlarmSetterService)
          .build()
      }
    }

    fun resetAndGetInstance(): RingerComponent {
      synchronized(this) {
        val instance = INSTANCE ?: throw Error("RingerComponent is not initialized.")
        INSTANCE = null
        return instance
      }
    }
  }

}