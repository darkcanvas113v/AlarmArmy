package com.sillyapps.feature_next_alarm_setter.api

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.sillyapps.alarm_domain.AlarmRepository
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.di.NextAlarmSetterComponent
import com.sillyapps.feature_next_alarm_setter.service.NextAlarmSetterServiceImpl
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService

fun bindNextAlarmService(
  context: Context,
  alarmSetter: AlarmSetter,
  alarmRepository: AlarmRepository,
  serviceBoundCallback: (NextAlarmSetterService) -> Unit
): ServiceConnection {
  NextAlarmSetterComponent.initialize(context, alarmSetter, alarmRepository)
  return bindService(context, serviceBoundCallback)
}

private fun bindService(
  context: Context,
  serviceBoundCallback: (NextAlarmSetterService) -> Unit
): ServiceConnection {
  val connection = object : ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
      val binder = p1 as NextAlarmSetterServiceImpl.Binder
      serviceBoundCallback(binder.getService())
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
    }
  }

  context.bindService(Intent(context, NextAlarmSetterServiceImpl::class.java), connection, Context.BIND_AUTO_CREATE)

  return connection
}