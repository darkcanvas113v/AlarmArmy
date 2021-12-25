package com.sillyapps.alarm_scheduler.di

import com.sillyapps.alarm_scheduler.service.AlarmSchedulerServiceImpl
import kotlin.reflect.KProperty

class ComponentDelegate {
  operator fun getValue(thisRef: AlarmSchedulerServiceImpl, property: KProperty<*>): AlarmSchedulerComponent {
    val deps = AlarmSchedulerDependencies.getDeps()
    return DaggerAlarmSchedulerComponent.builder().context(deps.provideContext()).alarmDao(deps.provideAlarmDao()).build()
  }
}