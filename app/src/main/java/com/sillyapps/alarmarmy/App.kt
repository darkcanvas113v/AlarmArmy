package com.sillyapps.alarmarmy

import android.app.Application
import android.content.Intent
import com.sillyapps.alarm_alert.ui.AlarmAlertActivity
import com.sillyapps.alarm_data.di.DaggerAlarmDbComponent
import com.sillyapps.feature_alarm_setter.api.getAlarmSetter
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import timber.log.Timber

class App : Application() {

  val alarmComponent by lazy {
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

}