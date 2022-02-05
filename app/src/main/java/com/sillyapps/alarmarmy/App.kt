package com.sillyapps.alarmarmy

import android.app.Application
import android.content.Intent
import com.sillyapps.alarm_alert.ui.AlarmAlertActivity
import com.sillyapps.alarm_data.di.DaggerAlarmDbComponent
import com.sillyapps.app_api.ApplicationApi
import com.sillyapps.feature_alarm_setter.api.getAlarmSetter
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.api.initNextAlarmModule
import timber.log.Timber

class App : Application(), ApplicationApi {

  val alarmDbComponent by lazy {
    DaggerAlarmDbComponent.builder().context(applicationContext).build()
  }
  val alarmSetter: AlarmSetter by lazy {
    getAlarmSetter(
      context = applicationContext,
      ringerIntent = Intent(applicationContext, AlarmAlertActivity::class.java)
        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
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