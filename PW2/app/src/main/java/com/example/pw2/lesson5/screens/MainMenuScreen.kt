package com.example.pw2.lesson5.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.pw2.lesson5.Screen
import com.example.pw2.lesson5.components.MenuButton
import com.example.pw2.lesson5.components.ScreenTitle

@Composable
fun MainMenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ScreenTitle("Контроль якості електроенергії")

        MenuButton("Нове вимірювання") {
            navController.navigate(Screen.ReadingInput.route)
        }
        MenuButton("Звіт за замовчуванням") {
            navController.navigate("report/230.0/50.0")
        }
    }
}
