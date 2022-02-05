package com.sillyapps.alarmarmy.ui

enum class Screen {
  AlarmList(),
  AlarmEditor(),
  Profile();

  companion object {
    fun fromRoute(route: String?): Screen =
      when (route?.substringBefore("/")) {
        AlarmList.name -> AlarmList
        AlarmEditor.name -> AlarmEditor
        Profile.name -> Profile
        null -> AlarmList
        else -> throw IllegalArgumentException("Route $route is not recognized.")
      }
  }
}