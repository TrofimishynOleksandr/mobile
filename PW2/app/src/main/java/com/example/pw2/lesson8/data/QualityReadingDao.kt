package com.example.pw2.lesson8.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QualityReadingDao {
    @Insert
    suspend fun insert(reading: QualityReadingEntity)

    @Query("SELECT * FROM quality_readings ORDER BY id DESC")
    suspend fun getAll(): List<QualityReadingEntity>
}
