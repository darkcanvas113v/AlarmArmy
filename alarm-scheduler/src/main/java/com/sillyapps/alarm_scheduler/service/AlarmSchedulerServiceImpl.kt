package com.sillyapps.alarm_scheduler.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.sillyapps.alarm_scheduler.di.ComponentDelegate
import com.sillyapps.alarm_scheduler.domain.AlarmScheduler
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Inject

class AlarmSchedulerServiceImpl: Service(), AlarmSchedulerService {

  private val binder = Binder()

  private val component by ComponentDelegate()

  private val serviceJob = Job()
  override val scope = CoroutineScope(Dispatchers.Main + serviceJob)

  @Inject lateinit var interactor: AlarmScheduler

  override fun onCreate() {
    super.onCreate()

    component.inject(this)
    interactor.initialize(this)
  }

  override fun setAlarm() {

  }

  override fun onBind(p0: Intent?): IBinder {
    return binder
  }

  inner class Binder: android.os.Binder() {
    fun getService(): AlarmSchedulerServiceImpl = this@AlarmSchedulerServiceImpl
  }

  override fun onDestroy() {
    super.onDestroy()
    serviceJob.cancel()
  }

}