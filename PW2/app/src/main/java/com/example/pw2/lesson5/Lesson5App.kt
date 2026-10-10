package com.example.pw2.lesson5

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pw2.lesson5.screens.MainMenuScreen
import com.example.pw2.lesson5.screens.ReadingInputScreen
import com.example.pw2.lesson5.screens.ReportScreen

@Composable
fun Lesson5App() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainMenu.route) {
        composable(Screen.MainMenu.route) { MainMenuScreen(navController) }
        composable(Screen.ReadingInput.route) { ReadingInputScreen(navController) }
        composable(
            route = Screen.Report.route,
            arguments = listOf(
                navArgument("voltage") { type = NavType.StringType },
                navArgument("frequency") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val voltage = backStackEntry.arguments?.getString("voltage")?.toDoubleOrNull() ?: 0.0
            val frequency = backStackEntry.arguments?.getString("frequency")?.toDoubleOrNull() ?: 0.0
            ReportScreen(navController, voltage, frequency)
        }
    }
}
