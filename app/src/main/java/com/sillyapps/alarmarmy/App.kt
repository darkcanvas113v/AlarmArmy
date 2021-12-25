package com.sillyapps.alarmarmy

import android.app.Application
import com.sillyapps.alarm_data.di.DaggerAlarmComponent
import timber.log.Timber

class App: Application() {

    val alarmComponent by lazy { DaggerAlarmComponent.builder().context(applicationContext).build() }

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