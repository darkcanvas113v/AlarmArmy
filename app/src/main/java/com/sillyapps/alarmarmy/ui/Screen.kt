package com.sillyapps.alarmarmy.ui

enum class Screen {
  Main(),
  Alarm(),
  Profile();

  companion object {
    fun fromRoute(route: String?): Screen =
      when (route?.substringBefore("/")) {
        Main.name -> Main
        Alarm.name -> Alarm
        Profile.name -> Profile
        null -> Main
        else -> throw IllegalArgumentException("Route $route is not recognized.")
      }
  }
}