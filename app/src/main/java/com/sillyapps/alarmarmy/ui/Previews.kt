package com.sillyapps.alarmarmy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sillyapps.alarm_editor_ui.components.PreviewNumberPicker
import com.sillyapps.alarm_editor_ui.components.RepeatButtonsPreview
import com.sillyapps.alarm_editor_ui.components.StateButtonPreview
import com.sillyapps.alarm_editor_ui.components.TimePickerPreview
import com.sillyapps.alarm_editor_ui.screen.AlarmEditorScreenPreview
import com.sillyapps.alarm_ui.screens.AlarmScreenPreview

// Wrapper functions to run previews from other modules

@Preview
@Composable
fun PreviewAlarmScreen() {
  AlarmScreenPreview()
}

@Preview
@Composable
fun PreviewTimePicker() {
  TimePickerPreview()
}

@Preview
@Composable
fun PreviewStateButton() {
  StateButtonPreview()
}

@Preview
@Composable
fun PreviewRepeatButtons() {
  RepeatButtonsPreview()
}

@Preview
@Composable
fun PreviewAlarmEditorScreen() {
  AlarmEditorScreenPreview()
}