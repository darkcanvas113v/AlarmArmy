package com.sillyapps.alarmarmy.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.sillyapps.core_ui.R
import com.sillyapps.core_ui.theme.DarkBlue900
import kotlinx.coroutines.launch

@Composable
fun Tabs(tabs: List<TabItem>, onTabClicked: (String) -> Unit) {
  var tabIndex by remember {
    mutableStateOf(0)
  }

  TabRow(
    selectedTabIndex = tabIndex
  ) {
    tabs.forEachIndexed { index, tab ->

      Tab(
        icon = { Icon(painter = painterResource(id = tab.icon), contentDescription = "") },
        text = { Text(tab.title) },
        selected = tabIndex == index,
        onClick = {
          tabIndex = index
          onTabClicked(tab.route)
        },
      )
    }
  }
}

sealed class TabItem(val icon: Int, val title: String, val route: String) {
  object Alarm: TabItem(R.drawable.ic_alarm, "Alarm", Screen.AlarmList.name)
  object Profiler: TabItem(R.drawable.ic_add, "Profiler", Screen.Profile.name)
}