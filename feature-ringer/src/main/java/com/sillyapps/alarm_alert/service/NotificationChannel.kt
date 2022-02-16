package com.sillyapps.alarm_alert.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.os.Build

fun setRingerNotificationChannel(context: Context) {
  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    // Create the NotificationChannel
    val name = "Show alarm"
    val descriptionText = "Notifies user of incoming alarms"
    val importance = NotificationManager.IMPORTANCE_HIGH
    val mChannel = NotificationChannel(RingerNotificationChannel.ID, name, importance)
    mChannel.description = descriptionText

    val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(mChannel)
  }
}

object RingerNotificationChannel {
  const val ID = "RINGER_NC_ID"
}