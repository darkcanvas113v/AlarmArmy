package com.sillyapps.feature_profiler.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.common_models.alarm.profiler.ProfilerState
import com.sillyapps.core_time.Time
import com.sillyapps.core_ui.compose.components.StateButton
import com.sillyapps.core_ui.compose.theme.AlarmArmyTheme
import com.sillyapps.core_ui.compose.theme.Red
import com.sillyapps.core_ui.compose.theme.Typography
import com.sillyapps.feature_profiler.ui.components.IntervalLayout
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm
import com.sillyapps.feature_profiler.ui.model.toProfilerModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun ProfilerScreen(
  profilerScreenStateHolder: ProfilerScreenStateHolder
) {

  val alarms by remember(profilerScreenStateHolder) {
    profilerScreenStateHolder.getProfilerAlarms()
  }.collectAsState(initial = listOf())

  val state by remember(profilerScreenStateHolder) {
    profilerScreenStateHolder.getProfilerState()
  }.collectAsState(initial = false)

  // TODO maybe optimize it a little bit?
  val wakingAlarms = alarms.filter { it.offset < 0L }.sortedBy { it.offset }
  val warningAlarms = alarms.filter { it.offset > 0L }.sortedBy { it.offset }

  var showAlarmDialog by remember {
    mutableStateOf(false)
  }

  var currentAlarm: UIProfilerAlarm by remember {
    mutableStateOf(UIProfilerAlarm.DEFAULT)
  }

  Surface(modifier = Modifier.fillMaxSize()) {
    Box() {

      Row() {
        Column(
          modifier = Modifier
            .fillMaxHeight()
            .padding(start = 8.dp),
          verticalArrangement = Arrangement.SpaceEvenly
        ) {
          Text(
            text = "Waking alarms",
            modifier = Modifier
              .vertical()
              .rotate(-90f),
            style = Typography.h5
          )

          Text(
            text = "Warning alarms",
            modifier = Modifier
              .vertical()
              .rotate(-90f),
            style = Typography.h5
          )
        }

        IntervalLayout(
          wakingAlarms = wakingAlarms,
          warningAlarms = warningAlarms,
          modifier = Modifier.padding(start = 8.dp, top = 16.dp, end = 32.dp, bottom = 16.dp),
          onEditItem = {
            currentAlarm = it
            showAlarmDialog = true
          },
          onDeleteItem = { profilerScreenStateHolder.deleteProfilerAlarm(it) }
        )
      }

      FloatingActionButton(
        onClick = {
          currentAlarm = UIProfilerAlarm.DEFAULT
          showAlarmDialog = true
        },
        modifier = Modifier
          .padding(16.dp)
          .align(Alignment.BottomEnd)
      ) {
        Icon(Icons.Filled.Add, "")
      }

      StateButton(
        text = if (state) "ON" else "OFF",
        state = state,
        setState = { profilerScreenStateHolder.setProfilerState(!state) },
        shape = MaterialTheme.shapes.medium,
        alternativeColor = Red,
        alternativeContentColor = Color.White,
        modifier = Modifier
          .align(Alignment.TopEnd)
          .padding(16.dp)
          .background(if (state) MaterialTheme.colors.primary else Red)
          .size(width = 80.dp, height = 42.dp)
      )
    }
  }

  if (showAlarmDialog) {
    AlarmDialog(
      onGetResult = {
        profilerScreenStateHolder.updateProfilerAlarm(it)
        showAlarmDialog = false
      },
      onDismiss = { showAlarmDialog = false },
      alarm = currentAlarm
    )
  }
}

@Preview
@Composable
fun ProfilerScreenPreview() {
  val alarms = remember {
    mutableStateListOf(
      -5 * Time.m, -10 * Time.m, 5 * Time.m
    ).map { ProfilerOffset(0, it).toProfilerModel() }
  }

  val state = remember {
    mutableStateOf(
      ProfilerState(enabled = true)
    )
  }

  val stateHolder = object : ProfilerScreenStateHolder {
    override fun getProfilerAlarms(): Flow<List<UIProfilerAlarm>> = flow {
      emit(alarms)
    }

    override fun updateProfilerAlarm(profilerAlarm: UIProfilerAlarm) {
    }

    override fun deleteProfilerAlarm(profilerAlarm: UIProfilerAlarm) {

    }

    override fun setProfilerState(state: Boolean) {

    }

    override fun getProfilerState(): Flow<Boolean> = flow {
      emit(state.value.enabled)
    }
  }

  AlarmArmyTheme {
    ProfilerScreen(stateHolder)
  }
}

fun Modifier.vertical() = layout { measurable, constraints ->
  val placeable = measurable.measure(constraints)
  layout(placeable.height, placeable.width) {
    placeable.place(
      x = -(placeable.width / 2 - placeable.height / 2),
      y = -(placeable.height / 2 - placeable.width / 2)
    )
  }
}