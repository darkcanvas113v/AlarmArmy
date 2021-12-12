package com.sillyapps.alarm_editor_ui.components

import android.widget.TimePicker
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.core.formatIfNeeded
import com.sillyapps.core.formatValue
import com.sillyapps.core_ui.dataToString
import com.sillyapps.core_ui.theme.AlarmArmyTheme
import com.sillyapps.core_ui.theme.Typography
import timber.log.Timber

@Composable
fun TimePicker(
  hours: Int,
  minutes: Int,
  onHoursChanged: (Int) -> Unit,
  onMinutesChanged: (Int) -> Unit,
) {
  val focusManager = LocalFocusManager.current

  Row(modifier = Modifier
    .fillMaxWidth()
    .padding(vertical = 32.dp),
    horizontalArrangement = Arrangement.Center) {
    TimePickerItem(
      value = hours,
      valueChanged = onHoursChanged,
      maxValue = 24,
      modifier = Modifier.align(Alignment.CenterVertically),
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
      ),
      keyboardActions = KeyboardActions(
        onNext = {
          focusManager.moveFocus(FocusDirection.Right)
        }
      )
    )
    Text(
      text = ":",
      style = Typography.h3,
      modifier = Modifier
        .align(Alignment.CenterVertically)
        .padding(horizontal = 4.dp),

      textAlign = TextAlign.Center
    )
    TimePickerItem(
      value = minutes,
      valueChanged = onMinutesChanged,
      maxValue = 60,
      modifier = Modifier.align(Alignment.CenterVertically),
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
  }
}

@Composable
private fun TimePickerItem(
  value: Int,
  valueChanged: (Int) -> Unit,
  maxValue: Int,
  modifier: Modifier = Modifier,
  keyboardOptions: KeyboardOptions,
  keyboardActions: KeyboardActions
) {
  val (typing, setTyping) = remember {
    mutableStateOf(false)
  }

  val (text, setText) = remember {
    mutableStateOf("")
  }

  if (!typing)
    setText(formatValue(value))

  TextField(
    value = text,
    onValueChange = { newText ->
      val typedCharacter = newText.last()
      if (!typedCharacter.isDigit()) return@TextField

      val newValue = newText.toIntOrNull()
      if (newValue == null || newValue < maxValue)
        setText(newText)
      else {
        setText(typedCharacter.toString())
      }
    },
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = true,
    textStyle = Typography.h3.copy(textAlign = TextAlign.Center),
    modifier = modifier
      .width(120.dp)
      .wrapContentHeight()
      .onFocusChanged {
        if (it.isFocused) {
          setText("")
          setTyping(true)
        }
        if (!it.isFocused) {
          val newValue = text.toIntOrNull() ?: 0
          setTyping(false)
          valueChanged(newValue)
        }

      }
  )

}

@Preview
@Composable
fun TimePickerPreview() {
  val (hours, setHours) = remember {
    mutableStateOf(0)
  }
  val (minutes, setMinutes) = remember {
    mutableStateOf(0)
  }
  AlarmArmyTheme {
    Surface() {
      TimePicker(hours = hours, minutes = minutes, onHoursChanged = setHours, onMinutesChanged = setMinutes)
    }
  }
}