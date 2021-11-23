package com.sillyapps.alarmarmy.ui

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.sillyapps.alarmarmy.R
import com.sillyapps.alarmarmy.convertMillisToStringFormat
import com.sillyapps.alarmarmy.convertMillisToStringFormatWithSeconds
import com.sillyapps.alarmarmy.data.Alarm
import org.w3c.dom.Text

@BindingAdapter("app:time")
fun TextView.setTime(time: Long) {
    text = convertMillisToStringFormat(time)
}

@BindingAdapter("app:timeWithSeconds")
fun TextView.setTimeWithSeconds(time: Long) {
    text = convertMillisToStringFormatWithSeconds(time)
}

@BindingAdapter("app:repeatingInfo")
fun TextView.setRepeatingInfo(alarm: Alarm) {
    if (!alarm.repeat) {
        text = context.getString(R.string.no_repeat)
        return
    }
    alarm.apply {
        if (repeatMonday && repeatTuesday && repeatWednesday && repeatThursday && repeatFriday && repeatSaturday && repeatSunday) {
            text = context.getString(R.string.repeat_every_day)
            return
        }
    }

    val str = StringBuilder()
    if (alarm.repeatMonday) str.append(context.getString(R.string.monday))
    if (alarm.repeatTuesday) str.append(context.getString(R.string.tuesday))
    if (alarm.repeatWednesday) str.append(context.getString(R.string.wednesday))
    if (alarm.repeatThursday) str.append(context.getString(R.string.thursday))
    if (alarm.repeatFriday) str.append(context.getString(R.string.friday))
    if (alarm.repeatSaturday) str.append(context.getString(R.string.saturday))
    if (alarm.repeatSunday) str.append(context.getString(R.string.sunday))

    text = str.toString()
}