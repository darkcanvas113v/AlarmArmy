package com.sillyapps.alarm_scheduler.data

import android.content.Context
import android.util.Log
import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_domain.model.Alarm
import com.sillyapps.alarm_scheduler.domain.AlarmSchedulerRepository
import com.sillyapps.alarm_scheduler.domain.model.SchedulerAlarm
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject

class AlarmSchedulerRepositoryImpl @Inject constructor(
  context: Context,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher,
  private val alarmDao: AlarmDao
): AlarmSchedulerRepository {

  private val listAdapter = Moshi.Builder().build().adapter<List<Long>>(Types.newParameterizedType(List::class.java, Long::class.javaObjectType))

  private val sharedPreferences = context.getSharedPreferences(ALARM_SCHEDULER_SHP, Context.MODE_PRIVATE)

  private val alarmsIDQueue: MutableStateFlow<List<Long>> = MutableStateFlow(emptyList())
  private val alarms = alarmDao.observeAll()

  private val alarmsQueue = combine(alarmsIDQueue, alarms, ::resolveAlarmQueue)

  override suspend fun loadQueue() = withContext(ioDispatcher) {
    val queueJson = sharedPreferences.getString(ALARM_QUEUE, "") ?: return@withContext
    if (queueJson.isBlank()) return@withContext

    alarmsIDQueue.value = listAdapter.fromJson(queueJson) ?: throw Error("Moshi returned null in queue list")
  }

  override suspend fun updateQueue(newQueue: List<SchedulerAlarm>): Unit = withContext(ioDispatcher) {
    alarmsIDQueue.value = newQueue.map { it.id }

    val json = listAdapter.toJson(alarmsIDQueue.value)
    sharedPreferences.edit().putString(ALARM_QUEUE, json).commit()
  }

  override fun getAlarms(): Flow<List<SchedulerAlarm>> = alarmDao.observeAll().map { it.map { alarm -> alarm.toDomainModel() } }

  override fun getQueue(): Flow<List<SchedulerAlarm>> = alarmsQueue

  private fun resolveAlarmQueue(queue: List<Long>, alarms: List<AlarmDto>): List<SchedulerAlarm> {
    return queue.map { alarmId ->
      alarms.find { alarmId == it.id }?.toDomainModel() ?: throw Exception("Cannot find alarm with id == $alarmId") }
  }

  companion object {
    const val ALARM_SCHEDULER_SHP = "ALARM_SCHEDULER_SHP"
    const val ALARM_QUEUE = "ALARM_QUEUE"
  }

}