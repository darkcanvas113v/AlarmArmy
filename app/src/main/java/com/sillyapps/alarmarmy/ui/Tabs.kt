package com.sillyapps.alarmarmy.ui

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.sillyapps.core_ui.R
import com.sillyapps.core_ui.compose.currentRoute

@Composable
fun Tabs(tabs: List<TabItem>, navController: NavController) {

  val currentRoute = currentRoute(navController = navController)
  val ind = tabs.indexOfFirst { it.route == currentRoute }
  val tabIndex =
    if (ind == -1) 0 else ind

  TabRow(
    selectedTabIndex = tabIndex
  ) {
    tabs.forEachIndexed { index, tab ->

      Tab(
        icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
        text = { Text(tab.title) },
        selected = currentRoute == tab.route,
        onClick = {
          if (currentRoute != tab.route)
            navController.navigate(tab.route) {
              popUpTo(Screen.AlarmList.name)
              launchSingleTop = true
            }
        },
      )
    }
  }
}

sealed class TabItem(val icon: Int, val title: String, val route: String) {
  object Alarm : TabItem(R.drawable.ic_alarm, "Alarm", Screen.AlarmList.name)
  object Profiler : TabItem(R.drawable.ic_add, "Profiler", Screen.Profile.name)
}