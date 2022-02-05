package com.sillyapps.feature_next_alarm_setter.api

import android.content.Context
import android.content.Intent
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.di.NextAlarmSetterComponent
import com.sillyapps.feature_next_alarm_setter.service.NextAlarmSetterServiceImpl

fun initNextAlarmModule(
  context: Context,
  alarmSetter: AlarmSetter,
  alarmRepository: AlarmRepository,
  currentAlarmRepository: CurrentAlarmRepository
): Intent {
  NextAlarmSetterComponent.initialize(context, alarmSetter, alarmRepository, currentAlarmRepository)

  return Intent(context, NextAlarmSetterServiceImpl::class.java)
}