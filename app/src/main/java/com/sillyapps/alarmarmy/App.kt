package com.sillyapps.alarmarmy

import android.app.Application
import android.content.Intent
import com.sillyapps.alarm_alert.RingerReceiver
import com.sillyapps.alarm_alert.service.setRingerNotificationChannel
import com.sillyapps.alarm_data.di.DaggerAlarmDbComponent
import com.sillyapps.alarm_domain.use_cases.UpdateCurrentAlarmUseCase
import com.sillyapps.alarmarmy.di.DaggerAppComponent
import com.sillyapps.app_api.ApplicationApi
import com.sillyapps.feature_alarm_setter.api.getAlarmSetter
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.api.initNextAlarmModule
import com.sillyapps.profiler_db.di.DaggerProfilerDbComponent
import kotlinx.coroutines.MainScope
import timber.log.Timber

class App : Application(), ApplicationApi {

  private val appComponent by lazy {
    DaggerAppComponent.builder().context(applicationContext).build() }

  private val appScope = MainScope()

  val alarmDbComponent by lazy {
    DaggerAlarmDbComponent.builder()
      .context(applicationContext)
      .coroutineScope(appScope)
      .database(appComponent.db)
      .sharedPref(appComponent.sharedPref)
      .build()
  }

  val profilerComponent by lazy {
    DaggerProfilerDbComponent.builder()
      .context(applicationContext)
      .coroutineScope(appScope)
      .database(appComponent.db)
      .sharedPref(appComponent.sharedPref)
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

    setRingerNotificationChannel(applicationContext)
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
      currentAlarmRepository = alarmDbComponent.currentAlarmRepository,
      profilerRepository = profilerComponent.profilerRepository
    )
  }

}