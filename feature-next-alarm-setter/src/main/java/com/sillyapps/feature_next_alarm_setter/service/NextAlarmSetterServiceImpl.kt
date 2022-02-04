package com.sillyapps.feature_next_alarm_setter.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.di.NextAlarmSetterComponent
import com.sillyapps.feature_next_alarm_setter.domain.GetNextAlarmByDozeUseCase
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

internal class NextAlarmSetterServiceImpl: Service(), NextAlarmSetterService {

  private val binder = Binder()

  private val serviceJob = Job()
  private val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  @Inject
  lateinit var getNextAlarmUseCase: GetClosestActiveAlarmUseCase
  @Inject
  lateinit var getNextAlarmByDozeUseCase: GetNextAlarmByDozeUseCase
  @Inject
  lateinit var alarmSetter: AlarmSetter


  override fun onCreate() {
    super.onCreate()

    val component = NextAlarmSetterComponent.resetAndGetInstance()
    component.inject(this)
  }

  override fun onBind(p0: Intent?): IBinder {
    return binder
  }

  inner class Binder: android.os.Binder() {
    fun getService(): NextAlarmSetterService = this@NextAlarmSetterServiceImpl
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
  }

  override fun disableAlarm() {
    scope.launch {
      val nextAlarm = getNextAlarmUseCase().first()
      disableSkippedAlarms()
      nextAlarm?.let { setAlarm(it.remainingTime) }
      stopSelf()
    }
  }

  override fun doze() {
    scope.launch {
      val dozeDuration = getNextAlarmByDozeUseCase()

      if (dozeDuration == null) {
        disableAlarm()
        return@launch
      }
      setAlarm(dozeDuration)
      stopSelf()
    }
  }

  private fun setAlarm(triggerTime: Long) {
    alarmSetter.setAlarm(triggerTime)
  }

  private fun disableSkippedAlarms() {
    // TODO implement this
  }

}