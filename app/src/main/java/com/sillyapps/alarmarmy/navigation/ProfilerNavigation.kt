package com.sillyapps.alarmarmy.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.sillyapps.alarmarmy.ui.Screen
import com.sillyapps.feature_profiler.ui.api.ProfilerScreenNavigation
import com.sillyapps.profiler_db.di.ProfilerDbComponent

fun NavGraphBuilder.profilerGraph(
  navController: NavController,
  profilerDbComponent: ProfilerDbComponent
) {
  navigation(startDestination = Screen.Profile.name, route = ProfilerNavigation.profilerGraphRoute) {
    composable(Screen.Profile.name) {
      ProfilerScreenNavigation(profilerRepository = profilerDbComponent.profilerRepository)
    }
  }
}

object ProfilerNavigation {
  const val profilerGraphRoute = "profilerGraph"
}