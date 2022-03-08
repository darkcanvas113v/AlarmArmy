package com.sillyapps.feature_profiler.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.sillyapps.core_time.Time
import com.sillyapps.core_ui.compose.components.TimePickerItem
import com.sillyapps.core_ui.compose.theme.AlarmArmyTheme
import com.sillyapps.core_ui.compose.theme.Typography
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm

@Composable
fun AlarmDialog(
  onGetResult: (UIProfilerAlarm) -> Unit,
  onDismiss: () -> Unit,
  alarm: UIProfilerAlarm,
) {
  Dialog(
    onDismissRequest = { onDismiss() },
  ) {
    AlarmDialogContent(
      onGetResult = onGetResult,
      onDismiss = onDismiss,
      alarm = alarm)
  }
}

@Composable
fun AlarmDialogContent(
  onGetResult: (UIProfilerAlarm) -> Unit,
  onDismiss: () -> Unit,
  alarm: UIProfilerAlarm,
) {
  var offsetByMinutes by remember {
    mutableStateOf((alarm.offset / Time.m).toInt())
  }

  var sign by remember {
    mutableStateOf(alarm.sign)
  }

  val focusManager = LocalFocusManager.current

  Surface() {
    Column(modifier = Modifier.padding(16.dp)) {
      Text(text = "Pick offset", style = MaterialTheme.typography.subtitle1)

      TextButton(
        onClick = { sign = -sign },
        modifier = Modifier
          .padding(top = 16.dp)
          .align(Alignment.CenterHorizontally)
      ) {
        Text(
          text = if (sign == -1) "-" else "+",
          style = Typography.h3
        )
      }

      Column(
        modifier = Modifier
          .align(Alignment.CenterHorizontally)
      ) {


        TimePickerItem(
          value = offsetByMinutes,
          valueChanged = { offsetByMinutes = it },
          maxValue = 99,
//            modifier = Modifier.align(Alignment.CenterVertically),
          keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Number
          ),
          keyboardActions = KeyboardActions(
            onDone = {
              focusManager.clearFocus()
            }
          )
        )
        Text(
          text = "minutes",
          modifier = Modifier
            .align(Alignment.End)
            .padding(top = 4.dp)
        )

      }



      Row(
        modifier = Modifier
          .padding(top = 16.dp)
          .align(Alignment.End),
        horizontalArrangement = Arrangement.End
      ) {
        TextButton(
          onClick = onDismiss,
        ) {
          Text(text = "Cancel")
        }
        TextButton(
          onClick = {
            onGetResult(alarm.copy(offset = sign * offsetByMinutes * Time.m, sign = sign))
          }) {
          Text(text = "Confirm")
        }
      }
    }
  }
}

@Preview
@Composable
fun AlarmDialogPreview() {
  AlarmArmyTheme {
    AlarmDialogContent(onGetResult = {}, onDismiss = {}, alarm = UIProfilerAlarm.DEFAULT)
  }
}