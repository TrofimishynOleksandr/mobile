package com.example.pw2.lesson6

object QualityFormValidator {
    fun isStationNameValid(name: String): Boolean = name.isNotBlank()

    fun isVoltageValid(input: String): Boolean {
        val value = input.toDoubleOrNull() ?: return false
        return value in 190.0..250.0
    }

    fun isFrequencyValid(input: String): Boolean {
        val value = input.toDoubleOrNull() ?: return false
        return value in 49.0..51.0
    }

    fun isThdValid(input: String): Boolean {
        val value = input.toDoubleOrNull() ?: return false
        return value in 0.0..15.0
    }
}
