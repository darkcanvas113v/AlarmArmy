package com.sillyapps.alarmarmy.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sillyapps.alarm_data.di.DaggerAlarmComponent
import com.sillyapps.alarm_ui.di.AlarmScreenComponent
import com.sillyapps.alarm_ui.di.DaggerAlarmScreenComponent
import com.sillyapps.alarm_ui.screens.AlarmListViewModel
import com.sillyapps.alarm_ui.screens.AlarmScreen
import com.sillyapps.alarmarmy.di.AppComponent
import com.sillyapps.alarmarmy.di.DaggerAppComponent
import com.sillyapps.alarmarmy.navigation.AppNavHost
import com.sillyapps.core_ui.daggerViewModel
import com.sillyapps.core_ui.theme.AlarmArmyTheme

@Composable
fun MainApp(context: Context) {
  val appComponent = DaggerAppComponent.builder().context(context).build()

  AlarmArmyTheme {
    AppNavHost(appComponent = appComponent)
  }
}