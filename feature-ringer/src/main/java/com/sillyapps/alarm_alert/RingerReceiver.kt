package com.sillyapps.alarm_alert

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.sillyapps.alarm_alert.ui.AlarmAlertActivity

class RingerReceiver: BroadcastReceiver() {
  override fun onReceive(p0: Context?, p1: Intent?) {
    val intent = Intent(p0, AlarmAlertActivity::class.java)
      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    p0?.startActivity(intent)
  }
}