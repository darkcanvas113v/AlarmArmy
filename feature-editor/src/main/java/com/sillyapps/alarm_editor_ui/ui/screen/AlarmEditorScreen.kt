package com.sillyapps.alarm_editor_ui.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.alarm_editor_ui.ui.components.RepeatButtons
import com.sillyapps.core_ui.components.TimePicker
import com.sillyapps.alarm_editor_ui.ui.model.WeekDays
import com.sillyapps.alarm_editor_ui.ui.model.UIAlarm
import com.sillyapps.core_ui.theme.AlarmArmyTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun AlarmEditorScreen(
  stateHolder: AlarmEditorStateHolder,
  onConfirmationButtonClicked: () -> Unit
){
  val alarm by stateHolder.alarm.collectAsState(initial = UIAlarm())

  Surface(modifier = Modifier.fillMaxSize()) {
    Box {
      Column(Modifier.fillMaxSize()) {
        TimePicker(
          hours = alarm.timeHours,
          minutes = alarm.timeMinutes,
          onHoursChanged = stateHolder::hoursChanged,
          onMinutesChanged = stateHolder::minutesChanged)

        Divider()

        RepeatButtons(
          weekDays = alarm.weekDays,
          onEnableAll = stateHolder::enableAllDays,
          onDisableAll = stateHolder::disableAllDays,
          onToggleDay = stateHolder::toggleDay
        )
      }

      FloatingActionButton(
        onClick = {
          stateHolder.save()
          onConfirmationButtonClicked()
                  },
          modifier = Modifier.padding(16.dp).align(Alignment.BottomCenter)) {
          Icon(Icons.Filled.Check, "")
        }
    }
  }

}

@Preview
@Composable
fun AlarmEditorScreenPreview() {
  AlarmArmyTheme {
    Surface() {
      val data = MutableStateFlow(UIAlarm())

      val stateHolder = object : AlarmEditorStateHolder {
        override val alarm: Flow<UIAlarm> = data

        override fun enableAllDays() {
          data.value = data.value.copy(weekDays = WeekDays(
            mo = true, tu = true, we = true, th = true,
            fr = true, sa = true, su = true
          ))
        }

        override fun disableAllDays() {
          data.value = data.value.copy(weekDays = WeekDays(
            mo = false, tu = false, we = false, th = false,
            fr = false, sa = false, su = false
          ))
        }

        override fun toggleDay(day: Int) {
          val repeat = data.value.weekDays

          val newRepeat =
            when (day) {
              com.sillyapps.core_time.AlarmConstants.mo -> repeat.copy(mo = !repeat.mo)
              com.sillyapps.core_time.AlarmConstants.tu -> repeat.copy(tu = !repeat.tu)
              com.sillyapps.core_time.AlarmConstants.we -> repeat.copy(we = !repeat.we)
              com.sillyapps.core_time.AlarmConstants.th -> repeat.copy(th = !repeat.th)
              com.sillyapps.core_time.AlarmConstants.fr -> repeat.copy(fr = !repeat.fr)
              com.sillyapps.core_time.AlarmConstants.sa -> repeat.copy(sa = !repeat.sa)
              com.sillyapps.core_time.AlarmConstants.su -> repeat.copy(su = !repeat.su)
              else -> throw Exception("No such day: $day")
            }

          data.value = data.value.copy(weekDays = newRepeat)
        }

        override fun save() {

        }

        override fun hoursChanged(hours: Int) {
          data.value = data.value.copy(timeHours = hours)
        }

        override fun minutesChanged(minutes: Int) {
          data.value = data.value.copy(timeMinutes = minutes)
        }

      }

      AlarmEditorScreen(stateHolder = stateHolder) {}
    }
  }
}