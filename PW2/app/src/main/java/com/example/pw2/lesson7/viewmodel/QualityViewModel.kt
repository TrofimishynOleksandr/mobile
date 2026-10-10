package com.example.pw2.lesson7.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

class QualityViewModel : ViewModel() {

    private val _voltage = MutableStateFlow(230.0)
    val voltage: StateFlow<Double> = _voltage.asStateFlow()

    private val _frequency = MutableStateFlow(50.0)
    val frequency: StateFlow<Double> = _frequency.asStateFlow()

    private val _status = MutableStateFlow("Норма")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    /** Асинхронно симулює отримання нового вимірювання з давача на підстанції. */
    fun refreshReading() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(800)

            val newVoltage = 200.0 + Random.nextDouble() * 60.0 // 200..260 В
            val newFrequency = 49.0 + Random.nextDouble() * 2.0 // 49..51 Гц

            _voltage.value = newVoltage
            _frequency.value = newFrequency
            _status.value = evaluateStatus(newVoltage, newFrequency)

            _isLoading.value = false
        }
    }

    private fun evaluateStatus(voltage: Double, frequency: Double): String {
        val voltageOk = voltage in 207.0..253.0
        val frequencyOk = frequency in 49.6..50.4
        return when {
            voltageOk && frequencyOk -> "Норма"
            !voltageOk && !frequencyOk -> "Критичне відхилення"
            else -> "Відхилення від норми"
        }
    }
}
