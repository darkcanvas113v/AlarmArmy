package com.sillyapps.alarm_alert.ui

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.*
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.app_api.ApplicationApi
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService
import timber.log.Timber

class AlarmAlertActivity : ComponentActivity() {

  private var ringtone: Ringtone? = null
  private var vibrator: Vibrator? = null

  private var nextAlarmSetterService: NextAlarmSetterService? = null
  private val connection: ServiceConnection by lazy {
    object : ServiceConnection {
      override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as NextAlarmSetterService.Binder
        nextAlarmSetterService = binder.getService()
      }

      override fun onServiceDisconnected(p0: ComponentName?) {
      }
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val nextAlarmServiceIntent = (application as ApplicationApi).initNextAlarmService()

    applicationContext.bindService(
      nextAlarmServiceIntent,
      connection,
      Context.BIND_AUTO_CREATE
    )

    setContent {
      AlarmAlertScreen(
        onStopButtonClick = { setNextAlarm() }
      )
    }

    playRingtone()
    vibrate()
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    showActivityIfDeviceIsSleeping()
  }

  private fun playRingtone() {
    val soundUri =
      RingtoneManager.getActualDefaultRingtoneUri(applicationContext, RingtoneManager.TYPE_ALARM)

    ringtone = RingtoneManager.getRingtone(applicationContext, soundUri)

    ringtone?.play()
  }

  private fun vibrate() {
    vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      (getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager).defaultVibrator
    } else {
      getSystemService(VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      vibrator?.vibrate(
        VibrationEffect.createOneShot(600000L, VibrationEffect.DEFAULT_AMPLITUDE)
      )
    } else {
      @Suppress("DEPRECATION")
      vibrator?.vibrate(600000L)
    }
  }

  private fun setNextAlarm() {
    if (nextAlarmSetterService == null) {
      Timber.d("NextAlarmService is not bound")
      return
    }
    nextAlarmSetterService!!.disableAlarm(this::shutOffAlarm)

  }

  private fun shutOffAlarm() {
    finish()
  }

  override fun onDestroy() {
    super.onDestroy()
    ringtone?.stop()
    vibrator?.cancel()
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

}