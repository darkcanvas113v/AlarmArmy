package com.sillyapps.alarm_alert

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.sillyapps.alarm_alert.ui.AlarmAlertActivity
import com.sillyapps.app_api.ApplicationApi
import com.sillyapps.core_ui.getImmutablePendingIntentFlags
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService

class RingerService: Service() {

  private val binder = Binder()

  private val ringer by lazy { Ringer(this) }
  private val notificationManager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

  private lateinit var nextAlarmSetterService: NextAlarmSetterService
  private val connection = object : ServiceConnection {
    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
      nextAlarmSetterService = (p1 as NextAlarmSetterService.Binder).getService()
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
    }
  }

  override fun onCreate() {
    super.onCreate()

    val nextAlarmServiceIntent = (application as ApplicationApi).initNextAlarmService()

    bindService(
      nextAlarmServiceIntent,
      connection,
      Context.BIND_AUTO_CREATE
    )

    goForeground()
    ringer.start()
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    if (intent != null) {
      when (intent.action) {
        DISABLE_ALARM -> {
          disable()
        }

        DOZE_ALARM -> {
          doze()
        }
      }
    }

    return START_NOT_STICKY
  }

  private fun goForeground() {
    val notificationBuilder = NotificationCompat.Builder(this, RingerNotificationChannel.ID)
      .setContentTitle("Alarm")
      .setContentText("Alarm is alarming!")
      .setSmallIcon(com.sillyapps.core_ui.R.drawable.ic_add)
      .setPriority(NotificationCompat.PRIORITY_HIGH)
      .setCategory(NotificationCompat.CATEGORY_ALARM)
      .setFullScreenIntent(AlarmAlertActivity.getIntent(applicationContext), true)
      .addAction(R.drawable.ic_add, "Disable", getDisableAlarmIntent(applicationContext))
      .addAction(R.drawable.ic_add, "Doze", getDozeAlarmIntent(applicationContext))

    val notification = notificationBuilder.build()
    startForeground(RINGER_NOTIFICATION_ID, notification)
  }

  fun doze() {
    ringer.stop()
    nextAlarmSetterService.doze(this::stopSelf)
  }

  fun disable() {
    ringer.stop()
    nextAlarmSetterService.disableAlarm(this::stopSelf)
  }

  override fun onBind(p0: Intent?): IBinder {
    return binder
  }

  override fun onDestroy() {
    super.onDestroy()

    ringer.stop()
    notificationManager.cancel(RINGER_NOTIFICATION_ID)
  }

  inner class Binder: android.os.Binder() {
    fun getService(): RingerService = this@RingerService
  }

  companion object {
    private const val requestCode = 0

    private const val DISABLE_ALARM = "DISABLE_ALARM"
    private const val DOZE_ALARM = "DOZE_ALARM"

    const val RINGER_NOTIFICATION_ID = 1

    fun getDisableAlarmIntent(context: Context): PendingIntent {
      val intent = Intent(context, RingerService::class.java)
      intent.action = DISABLE_ALARM
      return PendingIntent.getService(context, requestCode, intent, getImmutablePendingIntentFlags())
    }

    fun getDozeAlarmIntent(context: Context): PendingIntent {
      val intent = Intent(context, RingerService::class.java)
      intent.action = DOZE_ALARM
      return PendingIntent.getService(context, requestCode, intent, getImmutablePendingIntentFlags())
    }
  }
}