package com.sillyapps.alarmarmy

import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_data.common_alarm.AlarmRepositoryImpl
import com.sillyapps.alarm_scheduler.api.bindAlarmScheduler
import com.sillyapps.alarmarmy.ui.MainApp
import com.sillyapps.common_models.alarm.AlarmWithRemainingTime
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import javax.inject.Inject


class MainActivity : ComponentActivity() {

  private var alarmWatcherConnection: ServiceConnection? = null
  private var alarmSetter: AlarmSetter? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val app = application as App
    val alarmDbComponent = app.alarmDbComponent
    alarmSetter = app.alarmSetter

    alarmWatcherConnection = bindAlarmScheduler(
      context = this,
      alarmSetter = app.alarmSetter,
      alarmRepository = alarmDbComponent.alarmRepository,
      currentAlarmRepository = alarmDbComponent.currentAlarmRepository
    )

    setContent {
      MainApp(context = applicationContext, alarmDbComponent = alarmDbComponent)
    }

//    requestAlertWindowPermission()
  }

  private fun requestAlertWindowPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      if (!Settings.canDrawOverlays(this)) {
        val intent = Intent(
          Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
          Uri.parse("package:" + this.packageName)
        )
        // TODO replace deprecated method
        startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE)
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    alarmWatcherConnection?.let { unbindService(it) }
  }

  companion object {
    const val ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 101
  }
}