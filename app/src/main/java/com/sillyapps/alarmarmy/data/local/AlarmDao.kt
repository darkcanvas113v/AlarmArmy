package com.sillyapps.alarmarmy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sillyapps.alarmarmy.data.Alarm

@Dao
interface AlarmDao {

    @Query("select * from alarms")
    fun observeAllAlarms(): LiveData<List<Alarm>>

    @Query("select * from alarms where id = :id")
    suspend fun getAlarm(id: Long): Alarm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm): Long

}