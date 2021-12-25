package com.sillyapps.alarm_notifier

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AlarmAlertActivity: ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)

    setContent {
      AlarmAlertScreen(onStopButtonClick = { finish() })
    }
  }

}