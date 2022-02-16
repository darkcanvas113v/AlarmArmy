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
import com.sillyapps.alarm_alert.RingerService
import com.sillyapps.core_ui.getImmutablePendingIntentFlags
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService

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
        }
      )
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    unbindService(ringerConnection)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
//    showActivityIfDeviceIsSleeping()
  }

  private fun showActivityIfDeviceIsSleeping() {
    window.addFlags(
      WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
          or WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
    )

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
      setTurnScreenOn(true)
      setShowWhenLocked(true)
    } else {
      window.addFlags(
        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
            or WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
            or WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
      )
    }
  }

  companion object {
    fun getIntent(context: Context): PendingIntent {
      val intent = Intent(context, AlarmAlertActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

      return PendingIntent.getActivity(context, 0, intent, getImmutablePendingIntentFlags())
    }
  }

}