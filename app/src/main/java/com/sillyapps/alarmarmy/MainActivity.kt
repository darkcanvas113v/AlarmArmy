package com.sillyapps.alarmarmy

import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_scheduler.api.bindAlarmScheduler
import com.sillyapps.alarmarmy.ui.MainApp
import com.sillyapps.feature_alarm_setter_api.AlarmSetter


class MainActivity : ComponentActivity() {

  private var alarmWatcherConnection: ServiceConnection? = null
  private var alarmSetter: AlarmSetter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as App
    val alarmDbComponent = app.alarmDbComponent
    val profilerDbComponent = app.profilerAlarmComponent
    alarmSetter = app.alarmSetter

    alarmWatcherConnection = bindAlarmScheduler(
      context = this,
      alarmSetter = app.alarmSetter,
      alarmRepository = alarmDbComponent.alarmRepository,
      currentAlarmRepository = alarmDbComponent.currentAlarmRepository
    )

    setContent {
      MainApp(
        context = applicationContext,
        alarmDbComponent = alarmDbComponent,
        profilerDbComponent = profilerDbComponent)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    alarmWatcherConnection?.let { unbindService(it) }
  }
}