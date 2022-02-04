package com.sillyapps.core_ui

import android.app.Activity
import android.app.Service
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.ui.text.input.TextFieldValue
import com.sillyapps.core.convertMillisToStringFormatWithDays

fun TextFieldValue.dataToString(): String {
  return "text = $text, selection = (${selection.start}, ${selection.end}), composition = $composition"
}

fun Boolean.int(): Int {
  return if (this) 1 else 0
}

fun Service.showToast(string: String) {
  val mainHandler = Handler(mainLooper)

  mainHandler.post {
    Toast.makeText(
      applicationContext,
      string,
      Toast.LENGTH_LONG).show()
  }
}

fun showToast(context: Context, string: String) {
  Toast.makeText(context, string, Toast.LENGTH_LONG).show()
}