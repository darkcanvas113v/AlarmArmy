package com.sillyapps.alarmarmy

import android.app.Application
import timber.log.Timber

class App: Application() {

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