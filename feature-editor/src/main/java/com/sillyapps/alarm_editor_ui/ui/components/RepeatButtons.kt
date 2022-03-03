package com.sillyapps.alarm_editor_ui.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.alarm_editor_ui.ui.model.WeekDays
import com.sillyapps.core_ui.theme.AlarmArmyTheme

@Composable
fun RepeatButtons(
  weekDays: WeekDays,
  onEnableAll: () -> Unit,
  onDisableAll: () -> Unit,
  onToggleDay: (Int) -> Unit
) {

  Column {
    Box(modifier = Modifier
      .fillMaxWidth()
      .padding(top = 16.dp)) {
      Button(
        modifier = Modifier
          .align(Alignment.CenterStart)
          .padding(start = 16.dp),
        onClick = onEnableAll) {
        Text(text = "Every day")
      }

      Button(
        modifier = Modifier
          .align(Alignment.CenterEnd)
          .padding(end = 16.dp),
        onClick = onDisableAll) {
        Text(text = "Only once")
      }
    }

    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 32.dp)
    ) {
      DayButton(text = "Mo", state = weekDays.mo, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.mo) })
      DayButton(text = "Tu", state = weekDays.tu, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.tu) })
      DayButton(text = "We", state = weekDays.we, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.we) })
      DayButton(text = "Th", state = weekDays.th, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.th) })
    }

    Row(
      horizontalArrangement = Arrangement.SpaceEvenly,
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 16.dp, bottom = 32.dp)
    ) {
      DayButton(text = "Fr", state = weekDays.fr, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.fr) })
      DayButton(text = "Sa", state = weekDays.sa, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.sa) })
      DayButton(text = "Su", state = weekDays.su, setState = { onToggleDay(com.sillyapps.core_time.AlarmConstants.su) })
    }
  }

}

@Composable
fun DayButton(
  text: String,
  state: Boolean,
  setState: (Boolean) -> Unit
) {
  com.sillyapps.core_ui.components.StateButton(
    text = text,
    state = state,
    setState = setState,
    shape = CircleShape,
    alternativeColor = MaterialTheme.colors.error,
    alternativeContentColor = MaterialTheme.colors.onError,
    modifier = Modifier.size(64.dp)
  )
}

@Preview
@Composable
fun RepeatButtonsPreview() {
  AlarmArmyTheme {
    Surface() {
      val (repeat, setRepeat) = remember {
        mutableStateOf(WeekDays())
      }
      RepeatButtons(
        weekDays = repeat,
        onEnableAll = {
          setRepeat(WeekDays(
              mo = true, tu = true, we = true, th = true,
              fr = true, sa = true, su = true))
        },
        onDisableAll = {
          setRepeat(WeekDays(
            mo = false, tu = false, we = false, th = false,
            fr = false, sa = false, su = false
          ))
        },
        onToggleDay = {
          val newRepeat =
            when (it) {
              com.sillyapps.core_time.AlarmConstants.mo -> repeat.copy(mo = !repeat.mo)
              com.sillyapps.core_time.AlarmConstants.tu -> repeat.copy(tu = !repeat.tu)
              com.sillyapps.core_time.AlarmConstants.we -> repeat.copy(we = !repeat.we)
              com.sillyapps.core_time.AlarmConstants.th -> repeat.copy(th = !repeat.th)
              com.sillyapps.core_time.AlarmConstants.fr -> repeat.copy(fr = !repeat.fr)
              com.sillyapps.core_time.AlarmConstants.sa -> repeat.copy(sa = !repeat.sa)
              com.sillyapps.core_time.AlarmConstants.su -> repeat.copy(su = !repeat.su)
              else -> throw Exception("No such day: $it")
            }
          setRepeat(newRepeat)
        }
      )
    }
  }
}

@Preview
@Composable
fun StateButtonPreview() {
  AlarmArmyTheme {
    Surface() {
      val (state, setState) = remember {
        mutableStateOf(false)
      }
      DayButton(text = "mo", state = state, setState = setState)
    }
  }
}