package com.example.pw2.lesson6

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class QualityFormValidatorTest {

    @Test
    fun `blank station name is invalid`() {
        assertFalse(QualityFormValidator.isStationNameValid(""))
        assertFalse(QualityFormValidator.isStationNameValid("   "))
    }

    @Test
    fun `non blank station name is valid`() {
        assertTrue(QualityFormValidator.isStationNameValid("ПС-110 Центральна"))
    }

    @Test
    fun `voltage within 190-250 is valid`() {
        assertTrue(QualityFormValidator.isVoltageValid("230"))
        assertTrue(QualityFormValidator.isVoltageValid("190"))
        assertTrue(QualityFormValidator.isVoltageValid("250"))
    }

    @Test
    fun `voltage outside range or non numeric is invalid`() {
        assertFalse(QualityFormValidator.isVoltageValid("189.9"))
        assertFalse(QualityFormValidator.isVoltageValid("250.1"))
        assertFalse(QualityFormValidator.isVoltageValid("abc"))
    }

    @Test
    fun `frequency within 49-51 is valid`() {
        assertTrue(QualityFormValidator.isFrequencyValid("50"))
        assertFalse(QualityFormValidator.isFrequencyValid("48.9"))
    }

    @Test
    fun `thd within 0-15 is valid`() {
        assertTrue(QualityFormValidator.isThdValid("5"))
        assertFalse(QualityFormValidator.isThdValid("-1"))
        assertFalse(QualityFormValidator.isThdValid("15.1"))
    }
}
