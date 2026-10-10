package com.example.pw2.lesson5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw2.lesson5.Screen
import com.example.pw2.lesson5.components.InfoRow
import com.example.pw2.lesson5.components.MenuButton
import com.example.pw2.lesson5.components.ScreenTitle
import com.example.pw2.lesson5.components.qualityStatus

@Composable
fun ReportScreen(navController: NavHostController, voltage: Double, frequency: Double) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        ScreenTitle("Звіт про якість електроенергії")

        InfoRow("Напруга", "$voltage В")
        InfoRow("Частота", "$frequency Гц")
        InfoRow("Статус", qualityStatus(voltage, frequency))

        Spacer(modifier = Modifier.height(16.dp))
        MenuButton("Повернутися в меню") {
            navController.navigate(Screen.MainMenu.route)
        }
    }
}
