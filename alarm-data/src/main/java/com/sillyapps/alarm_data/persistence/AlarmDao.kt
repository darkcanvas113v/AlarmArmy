package com.sillyapps.alarm_data.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

  @Query("select * from alarms")
  fun observeAll(): Flow<List<AlarmDto>>

  @Query("select * from alarms where id = :id")
  fun observeOne(id: Long): Flow<AlarmDto>

  @Insert
  suspend fun insert(alarm: AlarmDto)

  @Update
  suspend fun update(alarm: AlarmDto)

}