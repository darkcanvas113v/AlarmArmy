package com.sillyapps.feature_next_alarm_setter.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sillyapps.common_models.alarm.alarm.AlarmWithRemainingTime
import com.sillyapps.alarm_domain.use_cases.GetClosestActiveAlarmUseCase
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.di.NextAlarmSetterComponent
import com.sillyapps.feature_next_alarm_setter.domain.DisableAllSkippedAlarmUseCase
import com.sillyapps.feature_next_alarm_setter.domain.GetNextAlarmByDozeUseCase
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class NextAlarmSetterServiceImpl: Service(), NextAlarmSetterService {

  private val binder = Binder()

  private val serviceJob = Job()
  private val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  @Inject
  lateinit var getNextAlarmUseCase: GetClosestActiveAlarmUseCase
  @Inject
  lateinit var getNextAlarmByDozeUseCase: GetNextAlarmByDozeUseCase
  @Inject
  lateinit var disableAllSkippedAlarmUseCase: DisableAllSkippedAlarmUseCase
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

  inner class Binder: android.os.Binder(), NextAlarmSetterService.Binder {
    override fun getService(): NextAlarmSetterService = this@NextAlarmSetterServiceImpl
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
  }

  override fun disableAlarm(alarmIsSetCallback: () -> Unit) {
    scope.launch {
      disableAllSkippedAlarmUseCase()
      val nextAlarm = getNextAlarmUseCase().first()

      nextAlarm?.let {
        setAlarm(it, alarmIsSetCallback)
        return@launch
      }
      alarmIsSetCallback()
      stopSelf()
    }
  }

  override fun doze(alarmIsSetCallback: () -> Unit) {
    scope.launch {
      val nextAlarm = getNextAlarmByDozeUseCase()

      alarmSetter.setAlarm(nextAlarm.time)
      alarmIsSetCallback()
      stopSelf()
    }
  }

  private fun setAlarm(alarmWithRemainingTime: AlarmWithRemainingTime, alarmIsSetCallback: () -> Unit) {
    alarmSetter.setMainAlarm(alarmWithRemainingTime)
    alarmIsSetCallback()
    stopSelf()
  }

}