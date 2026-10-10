package com.example.pw2.lesson6

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pw2.lesson6.components.LoadingIndicator
import com.example.pw2.lesson6.components.ValidatedField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Lesson6App() {
    var stationName by remember { mutableStateOf("") }
    var voltageInput by remember { mutableStateOf("") }
    var frequencyInput by remember { mutableStateOf("") }
    var thdInput by remember { mutableStateOf("") }

    var stationNameError by remember { mutableStateOf(false) }
    var voltageError by remember { mutableStateOf(false) }
    var frequencyError by remember { mutableStateOf(false) }
    var thdError by remember { mutableStateOf(false) }

    var isLoading by remember { mutableStateOf(false) }
    var isSubmitted by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text("Реєстрація вимірювання якості електроенергії", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        ValidatedField(
            value = stationName,
            onValueChange = { stationName = it; stationNameError = false },
            label = "Назва підстанції",
            isError = stationNameError,
            errorText = "Поле не може бути порожнім",
            hintText = "Введіть назву контрольної точки мережі"
        )
        Spacer(modifier = Modifier.height(8.dp))

        ValidatedField(
            value = voltageInput,
            onValueChange = { voltageInput = it; voltageError = false },
            label = "Напруга (В)",
            isError = voltageError,
            errorText = "Введіть число від 190 до 250",
            hintText = "Номінальна напруга: 230 В"
        )
        Spacer(modifier = Modifier.height(8.dp))

        ValidatedField(
            value = frequencyInput,
            onValueChange = { frequencyInput = it; frequencyError = false },
            label = "Частота (Гц)",
            isError = frequencyError,
            errorText = "Введіть число від 49 до 51",
            hintText = "Номінальна частота: 50 Гц"
        )
        Spacer(modifier = Modifier.height(8.dp))

        ValidatedField(
            value = thdInput,
            onValueChange = { thdInput = it; thdError = false },
            label = "Коефіцієнт THD (%)",
            isError = thdError,
            errorText = "Введіть число від 0 до 15",
            hintText = "Допустиме значення: не більше 8%"
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isSubmitted = false

                stationNameError = !QualityFormValidator.isStationNameValid(stationName)
                voltageError = !QualityFormValidator.isVoltageValid(voltageInput)
                frequencyError = !QualityFormValidator.isFrequencyValid(frequencyInput)
                thdError = !QualityFormValidator.isThdValid(thdInput)

                val valid = !stationNameError && !voltageError && !frequencyError && !thdError

                if (valid) {
                    isLoading = true
                    scope.launch {
                        delay(800)
                        isLoading = false
                        isSubmitted = true
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зареєструвати вимірювання")
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            LoadingIndicator()
        }

        if (isSubmitted) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Вимірювання для \"$stationName\" успішно зареєстровано!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
