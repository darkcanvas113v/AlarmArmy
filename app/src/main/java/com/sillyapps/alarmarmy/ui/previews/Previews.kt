package com.sillyapps.alarmarmy.ui.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.sillyapps.alarm_editor_ui.ui.components.RepeatButtonsPreview
import com.sillyapps.alarm_editor_ui.ui.components.StateButtonPreview
import com.sillyapps.alarm_editor_ui.ui.screen.AlarmEditorScreenPreview
import com.sillyapps.alarm_ui.ui.AlarmScreenPreview
import com.sillyapps.feature_profiler.ui.screen.ProfilerScreenPreview

// Wrapper functions to run previews from other modules

@Preview
@Composable
fun PreviewAlarmScreen() {
  AlarmScreenPreview()
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

@Preview
@Composable
fun PreviewProfilerScreen() {
  ProfilerScreenPreview()
}