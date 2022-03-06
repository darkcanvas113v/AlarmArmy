package com.sillyapps.common_models.alarm.profiler

data class ProfilerAlarm(
  val time: Long,
  val state: State
) {
  enum class State {
    WAKING, TARGET, WARNING;

    companion object {
      fun fromInt(value: Int) = values().first { it.ordinal == value }
    }
  }
}

fun ProfilerOffset.toAlarm(targetAlarmTime: Long): ProfilerAlarm {
  return ProfilerAlarm(
    targetAlarmTime + offset,
    state = when {
      offset < 0L -> ProfilerAlarm.State.WAKING
      offset == 0L -> ProfilerAlarm.State.TARGET
      else -> ProfilerAlarm.State.WARNING
    }
  )
}
