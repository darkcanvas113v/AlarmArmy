package com.sillyapps.alarm_notifier

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.core_ui.theme.AlarmArmyTheme
import com.sillyapps.core_ui.theme.Typography

@Composable
fun AlarmAlertScreen(
  onStopButtonClick: () -> Unit
) {

  AlarmArmyTheme {
    Surface {
      Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
          Text(
            text = "Whoohooho! Alarm is alarming!",
            style = Typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp).padding(horizontal = 16.dp)
          )

          Button(
            onClick = onStopButtonClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
          ) {
            Text(text = "Stop it!")
          }
        }
      }
    }
  }

}

@Preview
@Composable
fun AlertScreenPreview() {
  AlarmAlertScreen(
    onStopButtonClick = {}
  )
}