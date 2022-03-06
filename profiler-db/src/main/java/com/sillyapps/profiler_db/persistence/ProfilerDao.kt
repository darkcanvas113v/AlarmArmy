package com.sillyapps.profiler_db.persistence

import androidx.room.*
import com.sillyapps.profiler_db.model.ProfilerOffsetDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProfilerDao {

  @Query("select * from profiler_alarm where id = :id")
  fun get(id: Long): ProfilerOffsetDto?

  @Query("select * from profiler_alarm")
  fun observeAll(): Flow<List<ProfilerOffsetDto>>

  @Query("select * from profiler_alarm where id = :id")
  fun observeOne(id: Long): Flow<ProfilerOffsetDto>

  @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun insert(offset: ProfilerOffsetDto): Long

  @Update
  suspend fun update(offset: ProfilerOffsetDto)

  @Delete
  suspend fun delete(offset: ProfilerOffsetDto)

  @Transaction
  suspend fun upsert(offset: ProfilerOffsetDto) {
    val id = insert(offset)

    if (id == -1L) {
      update(offset)
    }
  }

}