package com.sillyapps.alarm_data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sillyapps.alarm_data.model.AlarmDto

@Database(entities = [AlarmDto::class], version = 2, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

  abstract val alarmDao: AlarmDao

  companion object {
    @Volatile
    private var INSTANCE: AlarmDatabase? = null

    fun getInstance(context: Context): AlarmDatabase {
      synchronized(this) {
        var instance = INSTANCE

        if (instance == null) {
          instance = Room.databaseBuilder(
            context.applicationContext,
            AlarmDatabase::class.java,
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