package com.sillyapps.alarm_ui.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.alarm_ui.ui.components.AlarmItem
import com.sillyapps.alarm_ui.model.UIAlarm
import com.sillyapps.core_ui.theme.AlarmArmyTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun AlarmScreen(
  stateHolder: AlarmStateHolder,
  onAlarmClicked: (Long) -> Unit
) {
  val alarms by remember(stateHolder) { stateHolder.getAlarms() }
    .collectAsState(initial = listOf())

  Surface(modifier = Modifier.fillMaxSize()) {
    Box {
      LazyColumn(
        contentPadding = PaddingValues(top = 16.dp),
        modifier = Modifier.fillMaxSize()
      ) {
        items(items = alarms) { alarm ->
          AlarmItem(
            item = alarm,
            onToggleActive = stateHolder::toggleAlarm,
            onClick = onAlarmClicked)
        }
      }

      FloatingActionButton(
        onClick = { onAlarmClicked(0) },
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
      UIAlarm(1, "09:00", true, "Once"),
      UIAlarm(2, "10:00", false, "Once"),
      UIAlarm(3, "12:00", true, "Once"),
      UIAlarm(4, "15:00", true, "Once"),
    )
  }

  val context = LocalContext.current

  val stateHolder = object : AlarmStateHolder {
    override fun getAlarms(): Flow<List<UIAlarm>> = flow {
      emit(alarmsPreview)
    }

    override fun toggleAlarm(alarmId: Long) {
      val alarm = alarmsPreview.find { it.id == alarmId }
      requireNotNull(alarm)
      val pos = alarmsPreview.indexOf(alarm)
      alarmsPreview[pos] =
        UIAlarm(alarm.id, alarm.time, !alarm.active, alarm.repeat)
    }

  }

  AlarmArmyTheme {
    AlarmScreen(stateHolder = stateHolder) {
      Toast.makeText(context,
        "Navigating to alarm with id = $it", Toast.LENGTH_SHORT).show()
    }
  }
}