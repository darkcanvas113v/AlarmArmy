package com.sillyapps.profiler_db.persistence

import android.content.Context
import com.sillyapps.profiler_db.model.ProfilerAlarmDto
import com.sillyapps.profiler_db.repositories.ProfilerDataSource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class ProfilerDataSourceImpl @Inject constructor(
  context: Context
): ProfilerDataSource {
  private val adapter = Moshi.Builder().build().adapter(ProfilerAlarmDto::class.java)

  private val sharedPreferences = context.getSharedPreferences(
    PROFILER_ALARM_SHARED_PREF,
    Context.MODE_PRIVATE
  )

  private val currentAlarm: MutableStateFlow<ProfilerAlarmDto?> = MutableStateFlow(null)

  companion object {
    const val PROFILER_ALARM_SHARED_PREF = "PROFILER_ALARM_SHARED_PREF"
    const val CURRENT_PROFILE = "CURRENT_PROFILE"
  }
}