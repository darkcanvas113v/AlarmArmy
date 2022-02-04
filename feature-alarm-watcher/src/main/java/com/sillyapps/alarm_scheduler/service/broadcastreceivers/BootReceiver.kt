package com.sillyapps.alarm_scheduler.service.broadcastreceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver: BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        p1?.let {
            if (it.action == Intent.ACTION_BOOT_COMPLETED) {
                // set Alarm
            }
        }
    }
}