package com.sillyapps.alarm_alert.ui

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.*
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_alert.service.RingerService
import com.sillyapps.core_ui.getImmutablePendingIntentFlags

class AlarmAlertActivity : ComponentActivity() {

  private lateinit var ringerService: RingerService
  private val ringerConnection = object : ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
      ringerService = (p1 as RingerService.Binder).getService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val intent = Intent(this, RingerService::class.java)
    bindService(intent, ringerConnection, BIND_IMPORTANT)

    setContent {
      AlarmAlertScreen(
        onStopButtonClick = {
          ringerService.disable()
          finish()
        },
        onDozeButtonClick = {
          ringerService.doze()
          finish()
        }
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    unbindService(ringerConnection)
  }

  companion object {
    fun getIntent(context: Context): PendingIntent {
      val intent = Intent(context, AlarmAlertActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

      return PendingIntent.getActivity(context, 0, intent, getImmutablePendingIntentFlags())
    }
  }

}