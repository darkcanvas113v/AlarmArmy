package com.sillyapps.feature_profiler.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sillyapps.core_time.Time
import com.sillyapps.core_ui.theme.*
import com.sillyapps.feature_profiler.R
import com.sillyapps.feature_profiler.ui.model.UIProfilerAlarm
import com.sillyapps.feature_profiler.ui.model.convertToProfilerUIModel

@Composable
fun IntervalLayout(
  wakingAlarms: List<UIProfilerAlarm>,
  warningAlarms: List<UIProfilerAlarm>
) {
  Box {
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
          IntervalLayoutItem(text = it.text, color = Blue)
        }
      }

      IntervalLayoutItem(
        text = "Target alarm",
        color = MaterialTheme.colors.primary,
        isMain = true)

      LazyColumn(
        modifier = Modifier.weight(1f)
      ) {
        items(items = warningAlarms) {
          IntervalLayoutItem(text = it.text, color = Red)
        }
      }
    }
  }
}

@Composable
fun IntervalLayoutItem(
  text: String,
  color: Color,
  isMain: Boolean = false
) {
  var circleSize = 24.dp
  var padding = 4.dp
  if (isMain) {
    circleSize = 32.dp
    padding = 0.dp
  }

  Box {
    Column(
      modifier = Modifier
        .padding(vertical = 16.dp)
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
      Text(text = "",
        modifier = Modifier
          .padding(bottom = 4.dp),
        style = Typography.h5
      )
    }

    Box(modifier = Modifier
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
      IntervalLayoutItem(
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
    listOf(-5 * Time.m, -10 * Time.m, -15 * Time.m).map { convertToProfilerUIModel(it) }
  }

  val warningAlarms = remember {
    listOf(5 * Time.m, 10 * Time.m, 15 * Time.m).map { convertToProfilerUIModel(it) }
  }

  AlarmArmyTheme {
    Surface(modifier = Modifier.fillMaxSize()) {
      IntervalLayout(wakingAlarms = wakingAlarms, warningAlarms = warningAlarms)
    }
  }
}
