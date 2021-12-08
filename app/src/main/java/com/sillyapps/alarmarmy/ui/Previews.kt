package com.sillyapps.alarmarmy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sillyapps.alarm_editor_ui.components.PreviewNumberPicker
import com.sillyapps.alarm_editor_ui.components.TimePickerPreview

// Wrapper functions to run previews from other modules

@Preview
@Composable
fun AlarmScreenPreview() {
  com.sillyapps.alarm_ui.screens.AlarmScreenPreview()
}

@Preview
@Composable
fun NumberPickerPreview() {
  PreviewNumberPicker()
}

@Preview
@Composable
fun PreviewTimePicker() {
  TimePickerPreview()
}