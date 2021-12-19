package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_data.di.IODispatcher
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_editor_ui.domain.AlarmEditorRepository
import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmEditorRepositoryImpl @Inject constructor(
  private val alarmDao: AlarmDao,
  private val editorDataSource: AlarmEditorDataSource,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
): AlarmEditorRepository {

  override fun getAlarm() = editorDataSource.getAlarm()

  override suspend fun loadAlarm(id: Long) = withContext(ioDispatcher) {
    val alarm = alarmDao.get(id)
    editorDataSource.update(alarm.toDomainModel())
  }

  override suspend fun saveAlarm() = withContext(ioDispatcher) {
    alarmDao.upsert(editorDataSource.getAlarm().first().toDataModel())
  }

  // TODO use ioDispatcher?
  override suspend fun update(alarm: EditorAlarm) = withContext(ioDispatcher) {
    editorDataSource.update(alarm)
  }
}