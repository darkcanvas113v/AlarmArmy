package com.sillyapps.alarmarmy

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
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