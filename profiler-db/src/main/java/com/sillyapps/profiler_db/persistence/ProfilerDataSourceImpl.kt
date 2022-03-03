package com.sillyapps.profiler_db.persistence

import android.content.SharedPreferences
import com.sillyapps.profiler_db.model.ProfilerStateDto
import com.sillyapps.profiler_db.repositories.ProfilerDataSource
import com.squareup.moshi.Moshi
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

  private val profilerState = MutableStateFlow(ProfilerStateDto())

  override suspend fun load() {
    val json = sharedPreferences.getString(CURRENT_ALARM, "") ?: return
    if (json.isBlank() || json == "null") return

    try {
      profilerState.value = adapter.fromJson(json) ?: throw Error("Moshi returned null in $ProfilerDataSourceImpl")
    } catch (e: Exception) {
      Timber.e(e)
      Timber.w("Error in moshi, maybe you changed data class? Returning null and clearing current data state...")
      update(ProfilerStateDto())
      return
    }
  }

  override suspend fun update(state: ProfilerStateDto) {
    profilerState.value = state

    val json = adapter.toJson(profilerState.value)
    sharedPreferences.edit().putString(CURRENT_ALARM, json).commit()
  }

  override fun observe(): Flow<ProfilerStateDto> = profilerState

  companion object {
    const val CURRENT_ALARM = "CURRENT_ALARM"
  }
}