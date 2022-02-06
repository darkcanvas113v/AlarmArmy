package com.sillyapps.alarmarmy

import android.app.Application
import android.content.Intent
import com.sillyapps.alarm_alert.RingerReceiver
import com.sillyapps.alarm_data.di.DaggerAlarmDbComponent
import com.sillyapps.alarm_domain.use_cases.UpdateCurrentAlarmUseCase
import com.sillyapps.app_api.ApplicationApi
import com.sillyapps.feature_alarm_setter.api.getAlarmSetter
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.api.initNextAlarmModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.SupervisorJob
import timber.log.Timber

class App : Application(), ApplicationApi {

  private val appScope = MainScope()

  val alarmDbComponent by lazy {
    DaggerAlarmDbComponent.builder()
      .context(applicationContext)
      .coroutineScope(appScope)
      .build()
  }
  val alarmSetter: AlarmSetter by lazy {
    getAlarmSetter(
      context = applicationContext,
      ringerIntent = Intent(applicationContext, RingerReceiver::class.java),
      scope = appScope,
      updateCurrentAlarmUseCase = UpdateCurrentAlarmUseCase(alarmDbComponent.currentAlarmRepository)
    )
  }

  override fun onCreate() {
    initializeTimber()
    super.onCreate()
  }

  private fun initializeTimber() {
    if (BuildConfig.DEBUG) {
      Timber.plant(Timber.DebugTree())
    }
  }

  override fun initNextAlarmService(): Intent {
    return initNextAlarmModule(
      context = applicationContext,
      alarmSetter = alarmSetter,
      alarmRepository = alarmDbComponent.alarmRepository,
      currentAlarmRepository = alarmDbComponent.currentAlarmRepository
    )
  }

}