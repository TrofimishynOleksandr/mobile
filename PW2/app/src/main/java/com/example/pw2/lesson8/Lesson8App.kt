package com.example.pw2.lesson8

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.pw2.lesson8.data.QualityDatabase
import com.example.pw2.lesson8.data.QualityRepository
import com.example.pw2.lesson8.viewmodel.QualityStorageViewModel

@Composable
fun Lesson8App() {
    val context = LocalContext.current
    val db = remember {
        Room.databaseBuilder(context, QualityDatabase::class.java, "quality_db").build()
    }
    val repository = remember { QualityRepository(db.qualityReadingDao()) }

    val viewModel: QualityStorageViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return QualityStorageViewModel(repository) as T
            }
        }
    )

    var stationName by remember { mutableStateOf("") }
    var voltageInput by remember { mutableStateOf("") }
    var frequencyInput by remember { mutableStateOf("") }

    val readings by viewModel.readings.collectAsState()

    LaunchedEffect(Unit) { viewModel.loadAll() }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Журнал вимірювань якості електроенергії", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = stationName,
            onValueChange = { stationName = it },
            label = { Text("Назва підстанції") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = voltageInput,
            onValueChange = { voltageInput = it },
            label = { Text("Напруга (В)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = frequencyInput,
            onValueChange = { frequencyInput = it },
            label = { Text("Частота (Гц)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val voltage = voltageInput.toDoubleOrNull()
                val frequency = frequencyInput.toDoubleOrNull()
                if (stationName.isNotBlank() && voltage != null && frequency != null) {
                    viewModel.addReading(stationName, voltage, frequency)
                    stationName = ""
                    voltageInput = ""
                    frequencyInput = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зберегти вимірювання")
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Збережені вимірювання:", style = MaterialTheme.typography.titleMedium)

        LazyColumn {
            items(readings) { reading ->
                Text("${reading.stationName}: ${reading.voltage} В, ${reading.frequency} Гц — ${reading.status}")
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
