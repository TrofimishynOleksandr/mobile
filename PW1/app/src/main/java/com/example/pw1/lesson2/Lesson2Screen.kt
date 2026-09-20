package com.example.pw1.lesson2

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pw1.data.QualityCalculator
import com.example.pw1.data.QualityReading
import com.example.pw1.ui.theme.colorForStatus
import com.example.pw1.ui.theme.labelForStatus
import kotlin.math.roundToInt

@Composable
fun Lesson2Screen(modifier: Modifier = Modifier) {
    val calculator = remember { QualityCalculator() }

    var voltageInput by remember { mutableStateOf(QualityCalculator.NOMINAL_VOLTAGE.toString()) }
    var frequencyInput by remember { mutableStateOf(QualityCalculator.NOMINAL_FREQUENCY.toString()) }
    var thdInput by remember { mutableStateOf("3.5") }
    var powerFactorInput by remember { mutableStateOf("0.95") }

    var currentReading by remember { mutableStateOf<QualityReading?>(null) }
    val indexHistory = remember { mutableStateListOf<Float>() }

    fun calculate() {
        val reading = QualityReading(
            voltage = voltageInput.toDoubleOrNull() ?: QualityCalculator.NOMINAL_VOLTAGE,
            frequency = frequencyInput.toDoubleOrNull() ?: QualityCalculator.NOMINAL_FREQUENCY,
            thd = thdInput.toDoubleOrNull() ?: 0.0,
            powerFactor = powerFactorInput.toDoubleOrNull() ?: 1.0
        )
        currentReading = reading
        val index = calculator.calculateQualityIndex(reading)
        indexHistory.add(index.toFloat())
        if (indexHistory.size > 12) indexHistory.removeAt(0)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Заняття №2 · Просунутий рівень",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Параметри мережі вводяться користувачем, а обчислення виконує " +
                "окремий клас QualityCalculator.",
            style = MaterialTheme.typography.bodyMedium
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = voltageInput,
                onValueChange = { voltageInput = it },
                label = { Text("Напруга, В") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            OutlinedTextField(
                value = frequencyInput,
                onValueChange = { frequencyInput = it },
                label = { Text("Частота, Гц") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedTextField(
                value = thdInput,
                onValueChange = { thdInput = it },
                label = { Text("THD, %") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
            OutlinedTextField(
                value = powerFactorInput,
                onValueChange = { powerFactorInput = it },
                label = { Text("cos φ") },
                modifier = Modifier.fillMaxWidth().weight(1f)
            )
        }

        Button(onClick = ::calculate, modifier = Modifier.fillMaxWidth()) {
            Text("Розрахувати індекс якості")
        }

        currentReading?.let { reading ->
            val status = calculator.resolveStatus(reading)
            val index = calculator.calculateQualityIndex(reading)

            Card(
                colors = CardDefaults.cardColors(containerColor = colorForStatus(status).copy(alpha = 0.12f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Індекс якості: ${index.roundToInt()}% — ${labelForStatus(status)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = colorForStatus(status),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ParameterVerdictRow("Напруга", calculator.isVoltageInNorm(reading.voltage))
                    ParameterVerdictRow("Частота", calculator.isFrequencyInNorm(reading.frequency))
                    ParameterVerdictRow("THD", calculator.isThdInNorm(reading.thd))
                    ParameterVerdictRow("Коефіцієнт потужності", calculator.isPowerFactorInNorm(reading.powerFactor))
                }
            }
        }

        if (indexHistory.isNotEmpty()) {
            Text(
                text = "Динаміка індексу якості за останні вимірювання",
                style = MaterialTheme.typography.titleSmall
            )
            QualityLineChart(values = indexHistory)
        }
    }
}

@Composable
private fun ParameterVerdictRow(label: String, inNorm: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = if (inNorm) "У нормі" else "Відхилення",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}
