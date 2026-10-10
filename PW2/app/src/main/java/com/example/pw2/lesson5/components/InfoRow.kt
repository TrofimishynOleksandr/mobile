package com.example.pw2.lesson5.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(value, fontWeight = FontWeight.Bold)
    }
}

fun qualityStatus(voltage: Double, frequency: Double): String {
    val voltageOk = voltage in 207.0..253.0
    val frequencyOk = frequency in 49.6..50.4
    return when {
        voltageOk && frequencyOk -> "Норма"
        !voltageOk && !frequencyOk -> "Критичне відхилення"
        else -> "Відхилення від норми"
    }
}
