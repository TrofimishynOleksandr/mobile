package com.example.pw2.lesson8.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pw2.lesson8.data.QualityReadingEntity
import com.example.pw2.lesson8.data.QualityRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QualityStorageViewModel(private val repository: QualityRepository) : ViewModel() {

    private val _readings = MutableStateFlow<List<QualityReadingEntity>>(emptyList())
    val readings: StateFlow<List<QualityReadingEntity>> = _readings.asStateFlow()

    fun loadAll() {
        viewModelScope.launch {
            _readings.value = repository.getAll()
        }
    }

    fun addReading(stationName: String, voltage: Double, frequency: Double) {
        val voltageOk = voltage in 207.0..253.0
        val frequencyOk = frequency in 49.6..50.4
        val status = when {
            voltageOk && frequencyOk -> "Норма"
            !voltageOk && !frequencyOk -> "Критичне відхилення"
            else -> "Відхилення від норми"
        }
        viewModelScope.launch {
            repository.insert(
                QualityReadingEntity(
                    stationName = stationName,
                    voltage = voltage,
                    frequency = frequency,
                    status = status
                )
            )
            _readings.value = repository.getAll()
        }
    }
}
