package com.sillyapps.alarmarmy.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sillyapps.alarmarmy.data.Alarm

@Database(entities = [Alarm::class], version = 2, exportSchema = false)
//@TypeConverters(TypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val alarmDao: AlarmDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = instance
                return instance
            }
        }
    }
}
