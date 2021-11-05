package com.sillyapps.alarmarmy.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarms")
data class Alarm (
    @PrimaryKey(autoGenerate = true) val id: Long,
    val time: Long,
    val active: Boolean
    )

data class EditableAlarm(val id: Long = 0, var time: Long, var active: Boolean)