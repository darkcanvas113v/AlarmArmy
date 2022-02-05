package com.sillyapps.alarm_data.current_alarm

import android.content.Context
import com.sillyapps.alarm_data.current_alarm.model.AlarmData
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Error
import javax.inject.Inject

class CurrentAlarmDataSource @Inject constructor(
  context: Context
) {
  private val adapter = Moshi.Builder().build().adapter(AlarmData::class.java)

  private val sharedPreferences = context.getSharedPreferences(
    CURRENT_ALARM_SHARED_P,
    Context.MODE_PRIVATE
  )

  private val currentAlarm: MutableStateFlow<AlarmData?> = MutableStateFlow(null)

  fun load() {
    val json = sharedPreferences.getString(CURRENT_ALARM, "") ?: return
    if (json.isBlank() || json == "null") return

    currentAlarm.value = adapter.fromJson(json) ?: throw Error("Moshi returned null in queue list")
  }

  fun update(newAlarm: AlarmData?) {
    currentAlarm.value = newAlarm

    val json = adapter.toJson(currentAlarm.value)
    sharedPreferences.edit().putString(CURRENT_ALARM, json).commit()
  }

  fun observe(): Flow<AlarmData?> = currentAlarm

  companion object {
    const val CURRENT_ALARM_SHARED_P = "ALARM_SCHEDULER_SHP"
    const val CURRENT_ALARM = "CURRENT_ALARM"
  }

}