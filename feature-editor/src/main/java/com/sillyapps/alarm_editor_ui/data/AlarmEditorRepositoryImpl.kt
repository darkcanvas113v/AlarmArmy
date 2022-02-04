package com.sillyapps.alarm_editor_ui.data

import com.sillyapps.alarm_domain.use_cases.GetAlarmByIdUseCase
import com.sillyapps.alarm_domain.use_cases.UpdateAlarmUseCase
import com.sillyapps.alarm_editor_ui.domain.AlarmEditorRepository
import com.sillyapps.alarm_editor_ui.domain.model.EditorAlarm
import com.sillyapps.core_di.modules.IODispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlarmEditorRepositoryImpl @Inject constructor(
  private val getAlarmByIdUseCase: GetAlarmByIdUseCase,
  private val updateAlarmUseCase: UpdateAlarmUseCase,
  private val editorDataSource: AlarmEditorDataSource,
  @IODispatcher private val ioDispatcher: CoroutineDispatcher
): AlarmEditorRepository {

  override fun getAlarm() = editorDataSource.getAlarm()

  override suspend fun loadAlarm(id: Long) = withContext(ioDispatcher) {
    if (id == 0L) return@withContext
    val alarm = getAlarmByIdUseCase(id) ?: return@withContext

    editorDataSource.update(alarm.toDomainModel())
  }

  override suspend fun saveAlarm() = withContext(ioDispatcher) {
    updateAlarmUseCase(editorDataSource.getAlarm().first().toCommonModel())
  }

  override suspend fun update(alarm: EditorAlarm) = withContext(ioDispatcher) {
    editorDataSource.update(alarm)
  }
}