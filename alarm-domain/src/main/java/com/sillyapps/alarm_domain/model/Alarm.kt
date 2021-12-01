package com.sillyapps.alarm_domain.model

data class Alarm(
    val time: Long,
    val active: Boolean,
    val repeat: Int
)