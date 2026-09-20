package com.example.pw1.data

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QualityCalculatorTest {

    private val calculator = QualityCalculator()

    @Test
    fun pw_voltageInNorm_withinTolerance_isTrue() {
        assertTrue(calculator.isVoltageInNorm(230.0))
        assertTrue(calculator.isVoltageInNorm(253.0))
        assertTrue(calculator.isVoltageInNorm(207.0))
    }

    @Test
    fun pw_voltageInNorm_outsideTolerance_isFalse() {
        assertFalse(calculator.isVoltageInNorm(254.0))
        assertFalse(calculator.isVoltageInNorm(206.0))
    }

    @Test
    fun pw_frequencyInNorm_withinTolerance_isTrue() {
        assertTrue(calculator.isFrequencyInNorm(50.0))
        assertTrue(calculator.isFrequencyInNorm(50.19))
        assertTrue(calculator.isFrequencyInNorm(49.81))
    }

    @Test
    fun pw_frequencyInNorm_outsideTolerance_isFalse() {
        assertFalse(calculator.isFrequencyInNorm(50.21))
        assertFalse(calculator.isFrequencyInNorm(49.79))
    }

    @Test
    fun pw_thdInNorm_boundary() {
        assertTrue(calculator.isThdInNorm(8.0))
        assertFalse(calculator.isThdInNorm(8.01))
    }

    @Test
    fun pw_powerFactorInNorm_boundary() {
        assertTrue(calculator.isPowerFactorInNorm(0.92))
        assertFalse(calculator.isPowerFactorInNorm(0.9199))
    }

    @Test
    fun pw_calculateQualityIndex_nominalReading_is100() {
        val reading = QualityReading(voltage = 230.0, frequency = 50.0, thd = 0.0, powerFactor = 1.0)
        assertEquals(100.0, calculator.calculateQualityIndex(reading), 0.001)
    }

    @Test
    fun pw_resolveStatus_nominalReading_isNormal() {
        val reading = QualityReading(voltage = 230.0, frequency = 50.0, thd = 0.0, powerFactor = 1.0)
        assertEquals(QualityStatus.NORMAL, calculator.resolveStatus(reading))
    }

    @Test
    fun pw_resolveStatus_moderateDeviation_isWarning() {
        // Напруга поза нормою (дефіцит), решта параметрів у нормі — сукупний індекс близько 75%.
        val reading = QualityReading(voltage = 300.0, frequency = 50.0, thd = 0.0, powerFactor = 1.0)
        assertEquals(75.0, calculator.calculateQualityIndex(reading), 0.5)
        assertEquals(QualityStatus.WARNING, calculator.resolveStatus(reading))
    }

    @Test
    fun pw_resolveStatus_severeDeviation_isCritical() {
        val reading = QualityReading(voltage = 400.0, frequency = 52.0, thd = 20.0, powerFactor = 0.5)
        assertEquals(QualityStatus.CRITICAL, calculator.resolveStatus(reading))
    }

    @Test
    fun pw_generateReading_producesValuesWithinGeneratorRange() {
        val reading = calculator.generateReading()
        assertTrue(reading.voltage in 205.0..255.0)
        assertTrue(reading.frequency in 49.6..50.4)
        assertTrue(reading.thd in 1.0..11.0)
        assertTrue(reading.powerFactor in 0.85..1.0)
    }
}
