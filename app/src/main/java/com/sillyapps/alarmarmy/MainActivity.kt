package com.sillyapps.alarmarmy

import android.content.ServiceConnection
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sillyapps.alarm_scheduler.api.AlarmSetter
import com.sillyapps.alarm_scheduler.api.bindAlarmScheduler
import com.sillyapps.alarmarmy.ui.MainApp
import com.sillyapps.core.convertMillisToStringFormatWithDays
import com.sillyapps.core_ui.showToast

class MainActivity : ComponentActivity() {

    private var alarmWatcherConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val alarmComponent = (application as App).alarmComponent

        alarmWatcherConnection = bindAlarmScheduler(
            context = this,
            alarmSetter = object : AlarmSetter {
                override fun setAlarm(triggerTime: Long) {
                    showToast("Alarm will ring after ${convertMillisToStringFormatWithDays(triggerTime)}")
                }

                override fun cancelAlarm() {

                }

            },
            alarmRepository = alarmComponent.repository
        )

        setContent {
            MainApp(context = applicationContext, alarmDbComponent = alarmComponent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        alarmWatcherConnection?.let { unbindService(it) }
    }
}