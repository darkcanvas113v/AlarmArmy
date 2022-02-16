package com.sillyapps.feature_next_alarm_setter.data

import android.content.Context
import com.sillyapps.core_di.modules.IODispatcher
import com.sillyapps.core_time.Time
import com.sillyapps.feature_next_alarm_setter.domain.NextAlarmRepository
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Error
import javax.inject.Inject

class NextAlarmRepositoryImpl @Inject constructor(
  context: Context,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
): NextAlarmRepository {

  private val adapter = Moshi.Builder().build().adapter(Long::class.java)

  private val sharedPreferences = context.getSharedPreferences(
    DOZE_DURATION,
    Context.MODE_PRIVATE
  )

  override suspend fun getDozeDuration(): Long = withContext(ioDispatcher) {
    val json = sharedPreferences.getString(DOZE_DURATION, "")
    if (json.isNullOrBlank() || json == "null") return@withContext DEFAULT_DOZE_DURATION

    return@withContext adapter.fromJson(json) ?: throw Error("Moshi returned null doze duration")
  }

  companion object {
    const val DOZE_DURATION = "DOZE_DURATION"
    const val DEFAULT_DOZE_DURATION = 10 * Time.s
  }
}