package com.example.pw2.lesson8.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [QualityReadingEntity::class], version = 1, exportSchema = false)
abstract class QualityDatabase : RoomDatabase() {
    abstract fun qualityReadingDao(): QualityReadingDao
}
