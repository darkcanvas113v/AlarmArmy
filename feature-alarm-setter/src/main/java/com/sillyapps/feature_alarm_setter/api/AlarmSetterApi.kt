package com.sillyapps.feature_alarm_setter.api

import android.content.Context
import android.content.Intent
import com.sillyapps.alarm_domain.use_cases.UpdateCurrentAlarmUseCase
import com.sillyapps.feature_alarm_setter.AlarmSetterImpl
import com.sillyapps.feature_alarm_setter_api.AlarmSetter
import kotlinx.coroutines.CoroutineScope

fun getAlarmSetter(
  context: Context,
  ringerIntent: Intent,
  scope: CoroutineScope,
  updateCurrentAlarmUseCase: UpdateCurrentAlarmUseCase
): AlarmSetter {
  return AlarmSetterImpl(context, ringerIntent, scope, updateCurrentAlarmUseCase)
}