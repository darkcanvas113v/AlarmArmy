package com.sillyapps.core_android_services

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager

fun getDefaultRingtone(context: Context): Ringtone {
  val soundUri =
    RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)

  return RingtoneManager.getRingtone(context, soundUri)
}