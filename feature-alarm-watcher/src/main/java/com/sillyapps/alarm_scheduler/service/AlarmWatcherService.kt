package com.sillyapps.alarm_scheduler.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sillyapps.alarm_scheduler.di.AlarmWatcherComponent
import com.sillyapps.alarm_scheduler.domain.AlarmWatcher
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class AlarmWatcherService: Service() {

  private val binder = Binder()

  private val serviceJob = Job()
  private val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  @Inject lateinit var interactor: AlarmWatcher
  @Inject lateinit var alarmSetter: AlarmSetter

  override fun onCreate() {
    super.onCreate()

    val component = AlarmWatcherComponent.resetAndGetInstance()
    component.inject(this)

    interactor.initialize(alarmSetter, scope)
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

}