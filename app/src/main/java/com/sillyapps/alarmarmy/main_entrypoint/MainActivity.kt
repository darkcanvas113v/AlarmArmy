package com.sillyapps.alarmarmy.main_entrypoint

import android.content.ServiceConnection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_scheduler.api.bindAlarmScheduler
import com.sillyapps.alarmarmy.App
import com.sillyapps.alarmarmy.main_entrypoint.ui.MainApp

class MainActivity : ComponentActivity() {

  private var alarmWatcherConnection: ServiceConnection? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as App
    val alarmComponent = app.alarmComponent

    alarmWatcherConnection = bindAlarmScheduler(
      context = this,
      alarmSetter = app.alarmSetter,
      alarmRepository = alarmComponent.repository
    )

    setContent {
      MainApp(context = applicationContext, alarmDbComponent = alarmComponent)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    alarmWatcherConnection?.let { unbindService(it) }
  }
}