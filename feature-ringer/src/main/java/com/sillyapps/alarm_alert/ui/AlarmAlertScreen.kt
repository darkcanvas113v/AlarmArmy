package com.sillyapps.alarm_alert.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sillyapps.core_ui.compose.theme.AlarmArmyTheme
import com.sillyapps.core_ui.compose.theme.Typography

@Composable
fun AlarmAlertScreen(
  onStopButtonClick: () -> Unit,
  onDozeButtonClick: () -> Unit
) {

  AlarmArmyTheme {
    Surface {
      Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center)) {
          Text(
            text = "Whoohooho! Alarm is alarming!",
            style = Typography.h4,
            textAlign = TextAlign.Center,
            modifier = Modifier
              .padding(bottom = 16.dp)
              .padding(horizontal = 16.dp)
          )

          Row(
            modifier = Modifier
              .align(Alignment.CenterHorizontally)
              .padding(top = 16.dp)
          ) {
            Button(
              onClick = onStopButtonClick,
              modifier = Modifier
                .padding(end = 32.dp)
                .width(150.dp)
                .height(50.dp)
            ) {
              Text(text = "Stop it!")
            }

            Button(
              onClick = onDozeButtonClick,
              modifier = Modifier
                .width(150.dp)
                .height(50.dp)
            ) {
              Text(text = "Let me sleep!")
            }
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
    onStopButtonClick = {},
    onDozeButtonClick = {}
  )
}