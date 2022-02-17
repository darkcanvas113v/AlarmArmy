package com.sillyapps.feature_profiler.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sillyapps.feature_profiler.ui.components.IntervalLayout

@Composable
fun ProfilerScreen(
  profilerScreenStateHolder: ProfilerScreenStateHolder
) {

  val alarms by profilerScreenStateHolder.getProfilerAlarms().collectAsState(initial = listOf())

  // TODO maybe optimize it a little bit?
  val wakingAlarms = alarms.filter { it.offset < 0L }.sortedBy { it.offset }
  val warningAlarms = alarms.filter { it.offset > 0L }.sortedBy { it.offset }

  Surface(modifier = Modifier.fillMaxSize()) {
    Box {
      IntervalLayout(wakingAlarms = wakingAlarms, warningAlarms = warningAlarms)

      FloatingActionButton(
        onClick = {
        },
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.BottomCenter)) {
        Icon(Icons.Filled.Check, "")
      }
    }
  }
}