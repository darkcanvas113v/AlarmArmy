package com.sillyapps.alarmarmy.ringer_entrypoint

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.sillyapps.alarm_alert.ui.AlarmAlertActivity
import timber.log.Timber

class RingerBroadcastReceiver: BroadcastReceiver() {
  override fun onReceive(p0: Context?, p1: Intent?) {
    Timber.d("Alarm should ring now")

    val intent = Intent(p0, AlarmAlertActivity::class.java)
      .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    p0?.startActivity(intent)
  }
}