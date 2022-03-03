package com.sillyapps.alarmarmy.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sillyapps.alarm_data.common_alarm.model.AlarmDto
import com.sillyapps.alarm_data.persistence.AlarmDao
import com.sillyapps.alarm_data.persistence.AlarmDatabase
import com.sillyapps.profiler_db.model.ProfilerAlarmDto
import com.sillyapps.profiler_db.persistence.ProfilerDao
import com.sillyapps.profiler_db.persistence.ProfilerDatabase

@Database(entities = [AlarmDto::class, ProfilerAlarmDto::class], version = 4, exportSchema = false)
abstract class AppDatabase: RoomDatabase(), AlarmDatabase, ProfilerDatabase {

  abstract val alarmDao: AlarmDao

  abstract val profilerDao: ProfilerDao

  override fun provideAlarmDao(): AlarmDao {
    return alarmDao
  }

  override fun provideProfilerDao(): ProfilerDao {
    return profilerDao
  }

  companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getInstance(context: Context): AppDatabase {
      synchronized(this) {
        var instance = INSTANCE

        if (instance == null) {
          instance = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "alarm_database")
            .fallbackToDestructiveMigration()
            .build()

          INSTANCE = instance
        }

        return instance
      }
    }

  }
}