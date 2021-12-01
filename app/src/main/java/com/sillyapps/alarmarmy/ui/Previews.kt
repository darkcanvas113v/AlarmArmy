package com.sillyapps.alarmarmy.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

// Wrapper functions to run previews from other modules

@Preview
@Composable
fun AlarmScreenPreview() {
  com.sillyapps.alarm_ui.screens.AlarmScreenPreview()
}