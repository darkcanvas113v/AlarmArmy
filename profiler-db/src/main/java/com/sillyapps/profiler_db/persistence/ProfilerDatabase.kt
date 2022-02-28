package com.sillyapps.profiler_db.persistence

interface ProfilerDatabase {
  fun provideProfilerDao(): ProfilerDao
}