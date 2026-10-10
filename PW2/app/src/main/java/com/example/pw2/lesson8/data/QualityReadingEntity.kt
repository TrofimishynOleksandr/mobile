package com.example.pw2.lesson8.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quality_readings")
data class QualityReadingEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val stationName: String,
    val voltage: Double,
    val frequency: Double,
    val status: String
)
