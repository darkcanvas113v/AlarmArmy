package com.sillyapps.feature_alarm_setter

import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.sillyapps.alarm_domain.use_cases.UpdateCurrentAlarmUseCase
import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import com.sillyapps.core_di.modules.IOCoroutineScope
import com.sillyapps.core_time.convertMillisToStringFormatWithDays
import com.sillyapps.core_ui.getImmutablePendingIntentFlags
import com.sillyapps.core_ui.showToast
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

internal class AlarmSetterImpl(
  context: Context,
  ringerIntent: Intent,
  @IOCoroutineScope private val scope: CoroutineScope,
  private val updateCurrentAlarmUseCase: UpdateCurrentAlarmUseCase
) : AlarmSetter {

  private val pi by lazy {
    PendingIntent.getBroadcast(
      context,
      SHOW_ALARM_ACTIVITY,
      ringerIntent,
      getImmutablePendingIntentFlags()
    )
  }

  private val context = WeakReference(context)

  private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

  override fun setMainAlarm(alarm: AlarmWithRemainingTime) {
    updateCurrentAlarm(alarm)

    setAlarm(alarm.startupTime)
  }

  override fun setAlarm(time: Long) {
    val context = context.get() ?: return

    val untilString = "Alarm will ring after ${convertMillisToStringFormatWithDays(
      time - System.currentTimeMillis())}"

    // TODO create showIntent, to handle the situation when user clicks the alarm icon in the notification drawer
    val alarmInfo = AlarmManager.AlarmClockInfo(time, null)

    alarmManager.cancel(pi)

    // TODO make better handling for this, like showing a permission request launcher
    if (!scheduleExactAlarmPermissionIsGranted(context)) {
      showToast(context,
        "This app cannot schedule exact alarms, please consider granting permission"
      )
    }

    alarmManager.setAlarmClock(alarmInfo, pi)
    showToast(context, untilString)
  }

  override fun cancelAlarm() {
    scope.launch { updateCurrentAlarmUseCase(null) }

    alarmManager.cancel(pi)
  }

  private fun updateCurrentAlarm(alarmWithRemainingTime: AlarmWithRemainingTime) {
    if (alarmWithRemainingTime.id == 0L) return
    scope.launch { updateCurrentAlarmUseCase(alarmWithRemainingTime) }
  }

  private fun scheduleExactAlarmPermissionIsGranted(context: Context): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S)
      return true

    if (ContextCompat.checkSelfPermission(
        context, Manifest.permission.SCHEDULE_EXACT_ALARM
      ) == PackageManager.PERMISSION_GRANTED
    ) {
      return true
    }

    return false
  }

  companion object {
    private const val SHOW_ALARM_ACTIVITY = 1
  }

}