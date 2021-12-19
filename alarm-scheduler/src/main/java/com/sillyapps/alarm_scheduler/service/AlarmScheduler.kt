package com.sillyapps.alarm_scheduler.service

import android.app.Service
import android.content.Intent
import android.os.IBinder

class AlarmScheduler: Service() {

  private val binder = Binder()

  override fun onCreate() {
    super.onCreate()
  }

  override fun onBind(p0: Intent?): IBinder {
    return binder
  }

  inner class Binder: android.os.Binder() {
    fun getService(): AlarmScheduler = this@AlarmScheduler
  }

}