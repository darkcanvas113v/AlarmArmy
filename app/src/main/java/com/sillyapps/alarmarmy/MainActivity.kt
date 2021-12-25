package com.sillyapps.alarmarmy

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_data.di.DaggerAlarmComponent
import com.sillyapps.alarm_scheduler.di.AlarmSchedulerDependencies
import com.sillyapps.alarm_scheduler.service.AlarmSchedulerServiceImpl
import com.sillyapps.alarmarmy.ui.MainApp

class MainActivity : ComponentActivity() {

    private val alarmSchedulerIntent by lazy { Intent(this, AlarmSchedulerServiceImpl::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmComponent = (application as App).alarmComponent

        AlarmSchedulerDependencies.initialize(applicationContext, alarmComponent.getAlarmDao())
        startService(alarmSchedulerIntent)

        setContent {
            MainApp(context = applicationContext, alarmComponent = alarmComponent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(alarmSchedulerIntent)
    }
}