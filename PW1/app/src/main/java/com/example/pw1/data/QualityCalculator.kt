package com.example.pw1.data

import kotlin.math.abs
import kotlin.random.Random

class QualityCalculator {

    companion object {
        const val NOMINAL_VOLTAGE = 230.0
        const val VOLTAGE_TOLERANCE_PERCENT = 10.0

        const val NOMINAL_FREQUENCY = 50.0
        const val FREQUENCY_TOLERANCE = 0.2

        const val MAX_ALLOWED_THD = 8.0
        const val MIN_POWER_FACTOR = 0.92
    }

    fun generateReading(): QualityReading = QualityReading(
        voltage = Random.nextDouble(205.0, 255.0),
        frequency = Random.nextDouble(49.6, 50.4),
        thd = Random.nextDouble(1.0, 11.0),
        powerFactor = Random.nextDouble(0.85, 1.0)
    )

    fun voltageDeviationPercent(voltage: Double): Double =
        (voltage - NOMINAL_VOLTAGE) / NOMINAL_VOLTAGE * 100.0

    fun isVoltageInNorm(voltage: Double): Boolean =
        abs(voltageDeviationPercent(voltage)) <= VOLTAGE_TOLERANCE_PERCENT

    fun isFrequencyInNorm(frequency: Double): Boolean =
        abs(frequency - NOMINAL_FREQUENCY) <= FREQUENCY_TOLERANCE

    fun isThdInNorm(thd: Double): Boolean = thd <= MAX_ALLOWED_THD

    fun isPowerFactorInNorm(powerFactor: Double): Boolean = powerFactor >= MIN_POWER_FACTOR

    fun calculateQualityIndex(reading: QualityReading): Double {
        val voltageScore = (100.0 - abs(voltageDeviationPercent(reading.voltage)) * 4.0)
            .coerceIn(0.0, 100.0)
        val frequencyScore = (100.0 - abs(reading.frequency - NOMINAL_FREQUENCY) / FREQUENCY_TOLERANCE * 50.0)
            .coerceIn(0.0, 100.0)
        val thdScore = (100.0 - reading.thd / MAX_ALLOWED_THD * 60.0)
            .coerceIn(0.0, 100.0)
        val powerFactorScore = (reading.powerFactor / MIN_POWER_FACTOR * 100.0)
            .coerceIn(0.0, 100.0)

        return (voltageScore + frequencyScore + thdScore + powerFactorScore) / 4.0
    }

    fun resolveStatus(reading: QualityReading): QualityStatus {
        val allInNorm = isVoltageInNorm(reading.voltage) &&
            isFrequencyInNorm(reading.frequency) &&
            isThdInNorm(reading.thd) &&
            isPowerFactorInNorm(reading.powerFactor)
        val index = calculateQualityIndex(reading)

        return when {
            allInNorm && index >= 80.0 -> QualityStatus.NORMAL
            index >= 50.0 -> QualityStatus.WARNING
            else -> QualityStatus.CRITICAL
        }
    }
}
