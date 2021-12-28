package com.sillyapps.alarm_data.persistence

import androidx.room.*
import com.sillyapps.alarm_data.model.AlarmDto
import com.sillyapps.alarm_domain.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

  @Query("select * from alarms where id = :id")
  fun get(id: Long): AlarmDto?

  @Query("select * from alarms")
  fun observeAll(): Flow<List<AlarmDto>>

  @Query("select * from alarms where id = :id")
  fun observeOne(id: Long): Flow<AlarmDto>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(alarm: AlarmDto): Long

  @Update
  suspend fun update(alarm: AlarmDto)

  @Transaction
  suspend fun upsert(alarm: AlarmDto) {
    val id = insert(alarm)

    if (id == -1L) {
      update(alarm)
    }
  }

}