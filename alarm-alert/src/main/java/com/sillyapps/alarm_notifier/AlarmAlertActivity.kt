package com.sillyapps.alarm_notifier

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class AlarmAlertActivity: ComponentActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      AlarmAlertScreen(onStopButtonClick = { finish() })
    }
  }

  override fun onNewIntent(intent: Intent?) {
    super.onNewIntent(intent)

  }

}