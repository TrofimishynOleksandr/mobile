package com.example.pw2.lesson5

import com.example.pw2.lesson5.components.qualityStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class QualityStatusTest {

    @Test
    fun `nominal voltage and frequency give Norma`() {
        assertEquals("Норма", qualityStatus(230.0, 50.0))
    }

    @Test
    fun `voltage out of range gives deviation`() {
        assertEquals("Відхилення від норми", qualityStatus(260.0, 50.0))
    }

    @Test
    fun `frequency out of range gives deviation`() {
        assertEquals("Відхилення від норми", qualityStatus(230.0, 48.5))
    }

    @Test
    fun `both out of range gives critical`() {
        assertEquals("Критичне відхилення", qualityStatus(300.0, 45.0))
    }

    @Test
    fun `boundary voltage 207 is still ok`() {
        assertEquals("Норма", qualityStatus(207.0, 50.0))
    }

    @Test
    fun `boundary voltage 253 is still ok`() {
        assertEquals("Норма", qualityStatus(253.0, 50.0))
    }
}
