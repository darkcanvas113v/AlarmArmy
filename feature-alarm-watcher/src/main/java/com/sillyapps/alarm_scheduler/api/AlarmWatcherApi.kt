package com.sillyapps.alarm_scheduler.api

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.alarm_scheduler.di.AlarmWatcherComponent
import com.sillyapps.alarm_scheduler.service.AlarmWatcherService
import com.sillyapps.feature_alarm_setter_api.AlarmSetter

fun bindAlarmScheduler(
  context: Context,
  alarmSetter: AlarmSetter,
  alarmRepository: AlarmRepository
): ServiceConnection {
  AlarmWatcherComponent.initialize(context, alarmSetter, alarmRepository)
  return bindService(context)
}

private fun bindService(context: Context): ServiceConnection {
  val connection = object : ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
      val binder = p1 as AlarmWatcherService.Binder
      binder.getService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
    }
  }

  context.bindService(Intent(context, AlarmWatcherService::class.java), connection, Context.BIND_AUTO_CREATE)

  return connection
}