package com.sillyapps.feature_next_alarm_setter.api

import android.content.Context
import android.content.Intent
import com.sillyapps.alarm_domain.repositories.AlarmRepository
import com.sillyapps.alarm_domain.repositories.CurrentAlarmRepository
import com.sillyapps.common_profiler_usecases.ProfilerRepository
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import com.sillyapps.feature_next_alarm_setter.di.NextAlarmSetterComponent
import com.sillyapps.feature_next_alarm_setter.service.NextAlarmSetterServiceImpl

fun initNextAlarmModule(
  context: Context,
  alarmSetter: AlarmSetter,
  alarmRepository: AlarmRepository,
  currentAlarmRepository: CurrentAlarmRepository,
  profilerRepository: ProfilerRepository
): Intent {
  NextAlarmSetterComponent.initialize(context, alarmSetter, alarmRepository, currentAlarmRepository, profilerRepository)

  return Intent(context, NextAlarmSetterServiceImpl::class.java)
}