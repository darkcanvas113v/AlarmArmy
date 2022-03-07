package com.sillyapps.profiler_db.model

import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfilerStateDto(
  val state: Boolean = false
)

fun ProfilerStateDto.toCommonModel(): ProfilerState {
  return ProfilerState(enabled = state)
}

fun ProfilerState.toDataModel(): ProfilerStateDto {
  return ProfilerStateDto(state = enabled)
}