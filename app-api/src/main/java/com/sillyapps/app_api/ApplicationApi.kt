package com.sillyapps.app_api

import android.content.Intent
import android.content.ServiceConnection
import com.sillyapps.feature_next_alarm_setter_api.NextAlarmSetterService

interface ApplicationApi {
  fun initNextAlarmService(): Intent
}