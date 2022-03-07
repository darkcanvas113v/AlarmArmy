package com.sillyapps.feature_profiler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sillyapps.common_models.alarm.profiler.ProfilerOffset
import com.sillyapps.core_time.Time
import com.sillyapps.core_ui.compose.theme.*
import com.sillyapps.feature_profiler.R
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm
import com.sillyapps.feature_profiler.ui.model.toProfilerModel

@Composable
fun IntervalLayout(
  wakingAlarms: List<UIProfilerAlarm>,
  warningAlarms: List<UIProfilerAlarm>,
  modifier: Modifier = Modifier,
  onDeleteItem: (UIProfilerAlarm) -> Unit,
  onEditItem: (UIProfilerAlarm) -> Unit
) {
  Box(modifier = modifier) {
    Column(modifier = Modifier.align(Alignment.CenterStart)) {
      val width = 3.dp

      val padding = 14.5.dp
      val paddedWidth = width + padding

      Divider(
        color = Blue,
        modifier = Modifier
          .width(paddedWidth)
          .padding(start = padding)
          .weight(1f)
      )
      Divider(
        color = Red,
        modifier = Modifier
          .width(paddedWidth)
          .padding(start = padding)
          .weight(1f)
      )
    }

    Column() {
      LazyColumn(
        modifier = Modifier.weight(1f),
        reverseLayout = true
      ) {
        items(items = wakingAlarms) {
          IntervalItem(it, onEditItem, onDeleteItem)
        }
      }

      MainIntervalItem()

      LazyColumn(
        modifier = Modifier.weight(1f)
      ) {
        items(items = warningAlarms) {
          IntervalItem(it, onEditItem, onDeleteItem)
        }
      }
    }
  }
}

@Composable
fun MainIntervalItem() {
  BaseIntervalItem(
    circleSize = 32.dp,
    padding = 0.dp,
    text = "Target alarm",
    color = MaterialTheme.colors.primary
  )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun IntervalItem(
  alarm: UIProfilerAlarm,
  onEditItem: (UIProfilerAlarm) -> Unit,
  onDeleteItem: (UIProfilerAlarm) -> Unit
) {

  val dismissState = rememberDismissState(
    confirmStateChange = {
      if (it == DismissValue.DismissedToEnd) onDeleteItem(alarm)
      it != DismissValue.DismissedToEnd
    }
  )

  SwipeToDismiss(
    state = dismissState,
    directions = setOf(DismissDirection.StartToEnd),
    background = {}) {
    BaseIntervalItem(
      circleSize = 24.dp,
      padding = 4.dp,
      text = alarm.text,
      color = alarm.color,
      modifier = Modifier.clickable { onEditItem(alarm) }
    )
  }
}

@Composable
fun BaseIntervalItem(
  circleSize: Dp,
  padding: Dp,
  text: String,
  color: Color,
  modifier: Modifier = Modifier
) {

  Box(modifier = modifier) {
    Column(
      modifier = Modifier
        .padding(vertical = 8.dp)
        .padding(start = 4.dp)
        .fillMaxWidth()
    ) {
      Text(
        text = text,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 4.dp),
        textAlign = TextAlign.Center,
        style = Typography.h5
      )
      Divider(
        color = color,
        thickness = 3.dp,
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = 14.5.dp)
      )
      Text(
        text = "",
        modifier = Modifier
          .padding(bottom = 4.dp),
        style = Typography.h5
      )
    }

    Box(
      modifier = Modifier
        .padding(start = padding)
        .align(Alignment.CenterStart)
    ) {
      Image(
        painter = painterResource(id = R.drawable.ic_baseline_circle_24),
        contentDescription = "",
        colorFilter = ColorFilter.tint(color = color),
        modifier = Modifier
          .size(circleSize),
        alignment = Alignment.Center
      )
    }
  }
}

@Preview
@Composable
fun IntervalLayoutItemPreview() {
  AlarmArmyTheme {
    Surface {
      BaseIntervalItem(
        circleSize = 24.dp,
        padding = 4.dp,
        text = "-00:05",
        color = Green500
      )
    }
  }
}

@Preview
@Composable
fun IntervalLayoutPreview() {
  val wakingAlarms = remember {
    listOf(-5 * Time.m, -10 * Time.m, -15 * Time.m).map { ProfilerOffset(0, it).toProfilerModel() }
  }

  val warningAlarms = remember {
    listOf(5 * Time.m, 10 * Time.m, 15 * Time.m).map { ProfilerOffset(0, it).toProfilerModel() }
  }

  AlarmArmyTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      IntervalLayout(wakingAlarms = wakingAlarms, warningAlarms = warningAlarms, Modifier, {}, {})
    }
  }
}
