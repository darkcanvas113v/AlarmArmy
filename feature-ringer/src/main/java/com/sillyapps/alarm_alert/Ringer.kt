package com.sillyapps.alarm_alert

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import com.sillyapps.core_android_services.getDefaultRingtone
import com.sillyapps.core_android_services.getDefaultVibrator
import com.sillyapps.core_android_services.vibrateCompat

class Ringer(context: Context) {

  private val vibrator = getDefaultVibrator(context)
  private val ringtone = getDefaultRingtone(context)

  fun start() {
    ringtone.play()
    vibrator.vibrateCompat(duration = 60000L)
  }

  fun stop() {
    ringtone.stop()
    vibrator.cancel()
  }

}