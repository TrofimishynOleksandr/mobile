package com.example.pw1.data

data class QualityReading(
    val voltage: Double,
    val frequency: Double,
    val thd: Double,
    val powerFactor: Double
)

enum class QualityStatus { NORMAL, WARNING, CRITICAL }
