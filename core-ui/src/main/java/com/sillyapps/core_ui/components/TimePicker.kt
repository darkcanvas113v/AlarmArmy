package com.sillyapps.core_ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.core_time.convertToMillis
import com.sillyapps.core_time.formatValue
import com.sillyapps.core_ui.showToast
import com.sillyapps.core_ui.theme.AlarmArmyTheme
import com.sillyapps.core_ui.theme.Typography

@Composable
fun TimePicker(
  hours: Int,
  minutes: Int,
  onHoursChanged: (Int) -> Unit,
  onMinutesChanged: (Int) -> Unit,
) {
  val focusManager = LocalFocusManager.current

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 32.dp),
    horizontalArrangement = Arrangement.Center
  ) {
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
fun TimePickerItem(
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
    mutableStateOf(formatValue(value))
  }

  if (!typing)
    setText(formatValue(value))

  TextField(
    value = text,
    onValueChange = { newText ->
      val typedCharacter = newText.lastOrNull()
      if (typedCharacter != null && !typedCharacter.isDigit()) return@TextField

      val newValue = newText.toIntOrNull()
      when {
        newValue == null -> {
          setText(newText)
        }
        newValue < maxValue -> {
          setText(newText)
          valueChanged(newValue)
        }
        else -> {
          val typedValue = typedCharacter.toString().toInt()
          setText(typedCharacter.toString())
          valueChanged(typedValue)
        }
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
        if (!it.isFocused && typing) {
          val newValue = text.toIntOrNull() ?: 0
          setTyping(false)
//          valueChanged(newValue)
        }

      }
  )

}

@Preview
@Composable
fun TimePickerPreview() {
  var hours by remember {
    mutableStateOf(0)
  }
  var minutes by remember {
    mutableStateOf(0)
  }

  val context = LocalContext.current

  AlarmArmyTheme {
    Surface() {
      TimePicker(
        hours = hours,
        minutes = minutes,
        onHoursChanged = {
          hours = it
          showToast(context, "Time changed: ${convertToMillis(it, minutes)}")
        },
        onMinutesChanged = {
          minutes = it
          showToast(context, "Time changed: ${convertToMillis(hours, it)}")
        })
    }
  }
}