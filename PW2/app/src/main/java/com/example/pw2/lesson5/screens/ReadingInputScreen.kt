package com.example.pw2.lesson5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw2.lesson5.components.MenuButton
import com.example.pw2.lesson5.components.ScreenTitle

@Composable
fun ReadingInputScreen(navController: NavHostController) {
    var voltageInput by remember { mutableStateOf("230") }
    var frequencyInput by remember { mutableStateOf("50") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ScreenTitle("Нове вимірювання")

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

        Row {
            MenuButton("Сформувати звіт") {
                val v = voltageInput.toDoubleOrNull() ?: 0.0
                val f = frequencyInput.toDoubleOrNull() ?: 0.0
                navController.navigate("report/$v/$f")
            }
        }
    }
}
