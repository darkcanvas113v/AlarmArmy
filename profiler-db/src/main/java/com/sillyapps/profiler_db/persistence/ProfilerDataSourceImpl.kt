package com.sillyapps.profiler_db.persistence

import android.content.SharedPreferences
import com.sillyapps.profiler_db.model.ProfilerAlarmDto
import com.sillyapps.profiler_db.model.ProfilerStateDto
import com.sillyapps.profiler_db.repositories.ProfilerDataSource
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.adapter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

class ProfilerDataSourceImpl @Inject constructor(
  private val sharedPreferences: SharedPreferences
): ProfilerDataSource {

  private val adapter = Moshi.Builder().build().adapter(ProfilerStateDto::class.java)
  private val listAdapter: JsonAdapter<List<ProfilerAlarmDto>> = Moshi.Builder().build().adapter(
    Types.newParameterizedType(List::class.java, ProfilerAlarmDto::class.java)
  )

  private val profilerState = MutableStateFlow(ProfilerStateDto())

  private val profilerAlarms = MutableStateFlow(emptyList<ProfilerAlarmDto>())

  override suspend fun load() {
    loadModel(
      id = PROFILER_STATE,
      setModel = {
        profilerState.value = adapter.fromJson(it) ?: throw Error("Moshi returned null in $ProfilerDataSourceImpl")},
      onError = { updateState(ProfilerStateDto()) }
    )

    loadModel(
      id = PROFILER_ALARMS,
      setModel = {
        profilerAlarms.value = listAdapter.fromJson(it) ?: throw Error("Moshi returned null in $ProfilerDataSourceImpl")},
      onError = { updateProfilerAlarms(emptyList()) }
    )
  }

  private suspend fun loadModel(
    id: String,
    setModel: (String) -> Unit,
    onError: suspend () -> Unit
  ) {
    val json = sharedPreferences.getString(id, "") ?: return
    if (json.isBlank() || json == "null") return

    try {
      setModel(json)
    } catch (e: Exception) {
      Timber.e(e)
      Timber.w("Error in moshi, maybe you changed data class? Returning null and clearing current data state...")
      onError()
      return
    }
  }

  override suspend fun updateState(state: ProfilerStateDto) {
    profilerState.value = state

    val json = adapter.toJson(profilerState.value)
    sharedPreferences.edit().putString(PROFILER_STATE, json).commit()
  }

  override fun observeProfilerAlarms(): Flow<List<ProfilerAlarmDto>> {
    return profilerAlarms
  }

  override suspend fun updateProfilerAlarms(alarms: List<ProfilerAlarmDto>) {
    profilerAlarms.value = alarms

    val json = listAdapter.toJson(profilerAlarms.value)
    sharedPreferences.edit().putString(PROFILER_ALARMS, json).commit()
  }

  override fun observeState(): Flow<ProfilerStateDto> = profilerState

  companion object {
    const val PROFILER_STATE = "PROFILER_STATE"
    const val PROFILER_ALARMS = "PROFILER_ALARMS"
  }
}