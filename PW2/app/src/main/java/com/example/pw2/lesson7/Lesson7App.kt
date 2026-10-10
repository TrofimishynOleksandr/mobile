package com.example.pw2.lesson7

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pw2.lesson7.viewmodel.QualityViewModel

@Composable
fun Lesson7App(viewModel: QualityViewModel = viewModel()) {
    val voltage by viewModel.voltage.collectAsState()
    val frequency by viewModel.frequency.collectAsState()
    val status by viewModel.status.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Моніторинг якості електроенергії (MVVM)", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            Text("Отримання даних з давача...")
        } else {
            Text("Напруга: %.1f В".format(voltage))
            Text("Частота: %.2f Гц".format(frequency))
            Text("Статус: $status")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.refreshReading() }) {
            Text("Оновити показники")
        }
    }
}
