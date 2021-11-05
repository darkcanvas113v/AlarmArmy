package com.sillyapps.alarmarmy.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.sillyapps.alarmarmy.data.Alarm

@Dao
interface AlarmDao {

    @Query("select * from alarms")
    fun observeAllAlarms(): LiveData<List<Alarm>>

}