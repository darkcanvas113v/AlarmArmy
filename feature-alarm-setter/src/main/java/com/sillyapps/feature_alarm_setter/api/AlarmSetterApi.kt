package com.sillyapps.feature_alarm_setter.api

import android.content.Context
import android.content.Intent
import com.sillyapps.feature_alarm_setter.AlarmSetterImpl
import com.sillyapps.feature_alarm_setter_api.AlarmSetter

fun getAlarmSetter(context: Context, ringerIntent: Intent): AlarmSetter {
  return AlarmSetterImpl(context, ringerIntent)
}