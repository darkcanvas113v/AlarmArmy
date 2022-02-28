package com.sillyapps.profiler_db.persistence

import androidx.room.*
import com.sillyapps.profiler_db.model.ProfilerAlarmDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilerDao {

  @Query("select * from profiler_alarm where id = :id")
  fun get(id: Long): ProfilerAlarmDto?

  @Query("select * from profiler_alarm")
  fun observeAll(): Flow<List<ProfilerAlarmDto>>

  @Query("select * from profiler_alarm where id = :id")
  fun observeOne(id: Long): Flow<ProfilerAlarmDto>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(alarm: ProfilerAlarmDto): Long

  @Update
  suspend fun update(alarm: ProfilerAlarmDto)

  @Delete
  suspend fun delete(alarm: ProfilerAlarmDto)

  @Transaction
  suspend fun upsert(alarm: ProfilerAlarmDto) {
    val id = insert(alarm)

    if (id == -1L) {
      update(alarm)
    }
  }

}