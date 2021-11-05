package com.sillyapps.alarmarmy.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sillyapps.alarmarmy.convertMillisToStringFormat
import com.sillyapps.alarmarmy.convertMillisToStringFormatWithSeconds

@BindingAdapter("app:time")
fun TextView.setTime(time: Long) {
    text = convertMillisToStringFormat(time)
}

@BindingAdapter("app:timeWithSeconds")
fun TextView.setTimeWithSeconds(time: Long) {
    text = convertMillisToStringFormatWithSeconds(time)
}