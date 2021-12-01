package com.sillyapps.alarm_ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.alarm_ui.components.AlarmItem
import com.sillyapps.alarm_ui.model.Alarm
import com.sillyapps.core_ui.theme.AlarmArmyTheme

@Composable
fun AlarmScreen(
  stateHolder: AlarmStateHolder
) {
  Surface(modifier = Modifier.fillMaxSize()) {
    Box {
      LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),

      ) {
        items(items = stateHolder.alarms) { alarm ->
          AlarmItem(
            item = alarm,
            onToggleActive = stateHolder::toggleAlarm,
            onClick = stateHolder::onAlarmClicked)
        }
      }

      FloatingActionButton(
        onClick = { stateHolder.onAlarmClicked(0) },
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(bottom = 16.dp),
      ) {
        Icon(Icons.Filled.Add, "")
      }
    }
  }
}

@Preview
@Composable
fun AlarmScreenPreview() {
  val alarmsPreview = remember {
    mutableStateListOf(
      Alarm(1, "09:00", true, "Once"),
      Alarm(2, "10:00", false, "Once"),
      Alarm(3, "12:00", true, "Once"),
      Alarm(4, "15:00", true, "Once"),
    )
  }

  val stateHolder = object : AlarmStateHolder {
    override val alarms: List<Alarm> = alarmsPreview

    override fun toggleAlarm(alarmId: Long) {
      val alarm = alarms.find { it.id == alarmId }
      requireNotNull(alarm)
      val pos = alarms.indexOf(alarm)
      alarmsPreview[pos] =
        Alarm(alarm.id, alarm.time, !alarm.active, alarm.repeat)
    }

    override fun onAlarmClicked(alarmId: Long) {
    }
  }

  AlarmArmyTheme {
    AlarmScreen(stateHolder = stateHolder)
  }

}