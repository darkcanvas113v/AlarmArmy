package com.sillyapps.core_ui

import androidx.compose.ui.text.input.TextFieldValue

fun TextFieldValue.dataToString(): String {
  return "text = $text, selection = (${selection.start}, ${selection.end}), composition = $composition"
}