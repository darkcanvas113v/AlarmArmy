package com.sillyapps.alarm_scheduler.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sillyapps.alarm_scheduler.api.AlarmSetter
import com.sillyapps.alarm_scheduler.di.AlarmWatcherComponent
import com.sillyapps.alarm_scheduler.domain.AlarmScheduler
import com.sillyapps.alarm_scheduler.domain.AlarmSetterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class AlarmWatcherService: Service(), AlarmSetterService {

  private val binder = Binder()

  private val serviceJob = Job()
  override val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  /*private val pi by lazy {
    // TODO maybe better to do it with broadcastrecievers?
    val turnAlarmOnIntent = Intent(applicationContext, AlarmAlertActivity::class.java)
    turnAlarmOnIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

    var piFlags = PendingIntent.FLAG_UPDATE_CURRENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      piFlags = piFlags or PendingIntent.FLAG_IMMUTABLE
    }

    PendingIntent.getActivity(
      applicationContext,
      SHOW_ALARM_ACTIVITY,
      turnAlarmOnIntent,
      piFlags
    )
  }*/

  @Inject lateinit var interactor: AlarmScheduler
  @Inject lateinit var alarmSetter: AlarmSetter

  override fun onCreate() {
    super.onCreate()

    val component = AlarmWatcherComponent.resetAndGetInstance()
    component.inject(this)

    interactor.initialize(this)
  }

  override fun setAlarm(triggerTime: Long) {
    /*val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    // TODO create showIntent, to handle the situation when user clicks the alarm icon in the notification drawer
    val alarmInfo = AlarmManager.AlarmClockInfo(System.currentTimeMillis() + triggerTime, null)

    alarmManager.cancel(pi)

    // TODO make better handling for this, like showing a permission request launcher
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      when {
        ContextCompat.checkSelfPermission(
          applicationContext,
          Manifest.permission.SCHEDULE_EXACT_ALARM
        ) == PackageManager.PERMISSION_GRANTED -> {
          alarmManager.setAlarmClock(alarmInfo, pi)
          showToast("Alarm will ring after ${convertMillisToStringFormatWithDays(triggerTime)}")
        }
        else -> {
          showToast("This app cannot schedule exact alarms, please consider granting permission")
        }
      }
    }
    else {
      alarmManager.setAlarmClock(alarmInfo, pi)
      showToast("Alarm will ring after ${convertMillisToStringFormatWithDays(triggerTime)}")
    }*/

    alarmSetter.setAlarm(triggerTime)
  }

  override fun cancelAlarm() {
    /*val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

    alarmManager.cancel(pi)*/
    alarmSetter.cancelAlarm()
  }

  override fun onBind(p0: Intent?): IBinder {
    return binder
  }

  inner class Binder: android.os.Binder() {
    fun getService(): AlarmWatcherService = this@AlarmWatcherService
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
  }

  /*companion object {
    const val SHOW_ALARM_ACTIVITY = 0
  }*/

}