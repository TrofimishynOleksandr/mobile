package com.example.pw1.lesson3

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.pw1.R
import com.example.pw1.data.QualityCalculator
import com.example.pw1.data.QualityReading
import com.example.pw1.ui.theme.colorForStatus
import com.example.pw1.ui.theme.labelForStatus
import androidx.compose.foundation.Image

@Composable
fun Lesson3Screen(modifier: Modifier = Modifier) {
    val calculator = remember { QualityCalculator() }
    var reading by remember { mutableStateOf(calculator.generateReading()) }
    val status = calculator.resolveStatus(reading)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        MonitoringHeader(status = status)

        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ParameterCard(
                title = "Напруга",
                value = "%.1f В".format(reading.voltage),
                inNorm = calculator.isVoltageInNorm(reading.voltage),
                modifier = Modifier.width(160.dp)
            )
            ParameterCard(
                title = "Частота",
                value = "%.2f Гц".format(reading.frequency),
                inNorm = calculator.isFrequencyInNorm(reading.frequency),
                modifier = Modifier.width(160.dp)
            )
            ParameterCard(
                title = "THD",
                value = "%.1f %%".format(reading.thd),
                inNorm = calculator.isThdInNorm(reading.thd),
                modifier = Modifier.width(160.dp)
            )
            ParameterCard(
                title = "cos φ",
                value = "%.2f".format(reading.powerFactor),
                inNorm = calculator.isPowerFactorInNorm(reading.powerFactor),
                modifier = Modifier.width(160.dp)
            )
        }

        RefreshButton(onClick = { reading = calculator.generateReading() })
    }
}

@Composable
private fun MonitoringHeader(status: com.example.pw1.data.QualityStatus, modifier: Modifier = Modifier) {
    val statusColor = colorForStatus(status)

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Surface(
            shape = CircleShape,
            color = statusColor,
            modifier = Modifier.size(56.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_bolt),
                contentDescription = "Піктограма мережі електропостачання",
                modifier = Modifier
                    .padding(14.dp)
                    .fillMaxSize(),
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        Column {
            Text(
                text = "Заняття №3 · Просунутий рівень",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Панель моніторингу мережі — статус: ${labelForStatus(status)}",
                style = MaterialTheme.typography.bodyMedium,
                color = statusColor
            )
        }
    }
}

@Composable
private fun ParameterCard(
    title: String,
    value: String,
    inNorm: Boolean,
    modifier: Modifier = Modifier
) {
    val indicatorColor = if (inNorm) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.labelLarge)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                color = indicatorColor,
                shape = CircleShape,
                modifier = Modifier.size(8.dp)
            ) {}
        }
    }
}

@Composable
private fun RefreshButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier.fillMaxWidth()) {
        Text("Оновити всі показники")
    }
}
